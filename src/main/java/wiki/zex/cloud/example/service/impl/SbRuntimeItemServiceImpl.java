package wiki.zex.cloud.example.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import wiki.zex.cloud.example.entity.SbRuntimeItem;
import wiki.zex.cloud.example.entity.SbStation;
import wiki.zex.cloud.example.exception.ServerException;
import wiki.zex.cloud.example.mapper.SbRuntimeItemMapper;
import wiki.zex.cloud.example.req.SbRuntimeItemReq;
import wiki.zex.cloud.example.resp.SbRuntimeItemResp;
import wiki.zex.cloud.example.resp.SubStation;
import wiki.zex.cloud.example.service.IExcelService;
import wiki.zex.cloud.example.service.ISbRuntimeItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import wiki.zex.cloud.example.service.ISbStationService;
import wiki.zex.cloud.example.utils.ListUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 时刻表数据项 服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-06-15
 */
@Service
@Slf4j
public class SbRuntimeItemServiceImpl extends ServiceImpl<SbRuntimeItemMapper, SbRuntimeItem> implements ISbRuntimeItemService, IExcelService {


    private static final int TABLE_HEADER_COUNT = 3;
    private static final int TABLE_COLUMN_COUNT = 17;

    @Autowired
    private ISbStationService iSbStationService;

    @Override
    public void removeByTableId(Long id) {
        remove(new LambdaQueryWrapper<SbRuntimeItem>().eq(SbRuntimeItem::getTableId, id));
    }

    @Override
    public SbRuntimeItem create(SbRuntimeItemReq req) {
        SbRuntimeItem sbRuntimeItem = new SbRuntimeItem();
        BeanUtils.copyProperties(req, sbRuntimeItem);
        save(sbRuntimeItem);
        return sbRuntimeItem;
    }

    @Override
    public SbRuntimeItem update(Long id, SbRuntimeItemReq req) {
        SbRuntimeItem sbRuntimeItem = new SbRuntimeItem();
        BeanUtils.copyProperties(req, sbRuntimeItem);
        sbRuntimeItem.setId(id);
        updateById(sbRuntimeItem);
        return sbRuntimeItem;
    }

    @Override
    public IPage<SbRuntimeItemResp> page(Page<SbRuntimeItemResp> page, Long startStationId, Long endStationId, Long tableId, String serviceNo, String trainNo, Boolean up) {
        return baseMapper.page(page,startStationId,endStationId,tableId,serviceNo,trainNo,up);
    }

    @Override
    public void download(HttpServletResponse httpServletResponse) {

    }

    @Override
    @Transactional
    public void upload(MultipartFile file, HttpServletRequest request) throws IOException {
        String tableIdStr = request.getParameter("tableId");
        Long tableId = Long.valueOf(tableIdStr);
        //移除旧数据
        remove(new LambdaUpdateWrapper<SbRuntimeItem>().eq(SbRuntimeItem::getTableId,tableId));
        List<SbStation> downLine = iSbStationService.downLine();
        List<SbStation> upLine = iSbStationService.upLine();
        EasyExcel.read(file.getInputStream(), new AnalysisEventListener<Map<Integer, String>>() {
            final List<List<String>> data = new ArrayList<>();
            @Override
            public void invoke(Map<Integer, String> map, AnalysisContext context) {
                if (MapUtils.isEmpty(map)) {
                    return;
                }
                if (StringUtils.equals(map.get(0), "下行线") && CollectionUtils.isNotEmpty(data)) {
                    saveData(data, downLine, upLine, tableId);
                }
                List<String> item = new ArrayList<>();
                for (int i = 0; i < TABLE_COLUMN_COUNT; i++) {
                    item.add(map.get(i));
                }
                data.add(item);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                saveData(data, downLine, upLine, tableId);
                log.info("save data finished");
            }
        }).sheet().doRead();

    }

    private void saveData(List<List<String>> data, List<SbStation> downLine, List<SbStation> upLine, Long tableId) {


        int centerIndex = TABLE_COLUMN_COUNT / 2;
        List<List<String>> lists = ListUtils.rowToCol(data);

        List<SbRuntimeItem> items = new ArrayList<>();
        for (int i = 0; i < lists.size(); i++) {
            List<String> list = lists.get(i);
            if (i < centerIndex) {  //下行线
                combineData(items, list, downLine, tableId, false);
            } else if (i > centerIndex) { //上行线
                Collections.reverse(list);
                combineData(items, list, upLine, tableId, true);
            } else { //中心线
                List<String> subList = list.subList(TABLE_HEADER_COUNT, list.size() - TABLE_HEADER_COUNT);
                if (subList.size() != downLine.size() * 2) {
                    throw new ServerException("行车时刻表与车站信息不符");
                }
                for (int j = 0; j < downLine.size(); j++) {
                    SbStation sbStation = downLine.get(j);
                    if (!StringUtils.equals(sbStation.getStationName(), subList.get(j * 2)) || !StringUtils.equals(sbStation.getStationCode(), subList.get(j * 2 + 1))) {
                        throw new ServerException("行车时刻表与车站信息不符");
                    }
                }
            }
        }

        //保存数据
        saveBatch(items);
        //清空excel读取的数据
        data.clear();
    }


    private void combineData(List<SbRuntimeItem> items, List<String> list, List<SbStation> line, Long tableId, boolean up) {

        SbRuntimeItem item = new SbRuntimeItem();

        //所属时刻表
        item.setTableId(tableId);
        //是否上行
        item.setUp(up);
        //车次
        String trainNo = list.get(1);

        //判断车次是否为空 最后一个表有缺数据
        if (StringUtils.isEmpty(trainNo)){
            return;
        }
        item.setTrainNo(trainNo);
        //备注
        String description = list.get(2);
        item.setDescription(description);
        //服务号
        String serviceNo = list.get(list.size() - 2);
        item.setServiceNo(serviceNo);

        //列车站点信息
        List<SubStation> subStations = new ArrayList<>();

        for (float j = (float) TABLE_HEADER_COUNT / 2; j < (list.size() - (float) TABLE_HEADER_COUNT) / 2; j++) {

            String startAt = list.get((int) (j * 2));
            String endAt = list.get((int) (j * 2 + 1));
            if (StringUtils.isNotEmpty(startAt) || StringUtils.isNotEmpty(endAt)) {
                SubStation station = new SubStation();
                station.setStationId(line.get((int) (j) - TABLE_HEADER_COUNT / 2).getId());
                station.setStationCode(line.get((int) (j) - TABLE_HEADER_COUNT / 2).getStationCode());
                station.setStartAt(startAt);
                station.setEndAt(endAt);
                subStations.add(station);
            }
        }

        //始发站ID
        item.setStartStationId(subStations.get(0).getStationId());

        //终点站ID
        item.setEndStationId(subStations.get(subStations.size() - 1).getStationId());

        //开点
        item.setStartAt(formatTime(subStations.get(0).getStartAt()));
        //到点
        item.setEndAt(formatTime(subStations.get(subStations.size() - 1).getEndAt()));

        //总里程

        float distance = 0;

        boolean isRunning = false;
        //便利路线获取总里程
        for (SbStation sbStation : line) {
            if (sbStation.getId().equals(subStations.get(0).getStationId())) {
                //找到始发站 改变状态
                isRunning = true;
            } else if (sbStation.getId().equals(subStations.get(subStations.size() - 1).getStationId())) {
                //找到结束站 跳出循环
                break;
            }
            //如果是中间状态 加公里数
            if (isRunning) {
                distance += sbStation.getNextStationDistance();
            }
        }
        item.setDistance(distance);
        items.add(item);
    }

    private static LocalTime formatTime(String timeStr) {

        if (StringUtils.equals(timeStr,"▲")||StringUtils.equals(timeStr,"▼")){
            return null;
        }
        if (timeStr.length() == 10) {
            timeStr = "0" + timeStr;
        }

        int hour = StringUtils.equals(timeStr.substring(timeStr.length() -2), "上午") ? 0 : 12;
        timeStr = timeStr.substring(0,timeStr.length() -3);
        return LocalTime.parse(timeStr).plusHours(hour);
    }


    public static void main(String... args) {
        LocalTime localTime = formatTime("上午9:49:59");
        System.out.println(localTime);


    }
}
