package wiki.zex.cloud.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import wiki.zex.cloud.example.entity.SbStation;
import wiki.zex.cloud.example.mapper.SbStationMapper;
import wiki.zex.cloud.example.req.SbStationReq;
import wiki.zex.cloud.example.service.ISbStationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Zex
 * @since 2020-06-12
 */
@Service
public class SbStationServiceImpl extends ServiceImpl<SbStationMapper, SbStation> implements ISbStationService {

    @Override
    public SbStation create(SbStationReq req) {
        SbStation sbStation = new SbStation();
        BeanUtils.copyProperties(req, sbStation);
        save(sbStation);
        return sbStation;
    }

    @Override
    public SbStation update(Long id, SbStationReq req) {
        SbStation sbStation = new SbStation();
        BeanUtils.copyProperties(req, sbStation);
        sbStation.setId(id);
        updateById(sbStation);
        return sbStation;
    }

    @Override
    public List<SbStation> downLine() {
        List<SbStation> stations = list(new LambdaQueryWrapper<>());
        List<SbStation> downLine = new ArrayList<>();
        reserveStations(stations,null,downLine);
        Collections.reverse(downLine);
        return downLine;
    }

    @Override
    public List<SbStation> upLine() {
        List<SbStation> downLine = downLine();
        List<SbStation> upLine = new ArrayList<>();
        for (int i = downLine.size() - 1; i >= 0 ; i--) {

            SbStation station = downLine.get(i);
            if (i > 0){
                SbStation next = downLine.get(i - 1);
                station.setNextStationId(next.getId());
                station.setNextStationDistance(next.getNextStationDistance());
            }
         upLine.add(station);
        }
        return upLine;
    }

    private void reserveStations( List<SbStation> stations, Long nextId , List<SbStation> downLine){
        for (SbStation station : stations) {
            if ((station.getNextStationId() == null && nextId == null )||
                    (station.getNextStationId() != null &&station.getNextStationId().equals(nextId))){
                downLine.add(station);
                reserveStations(stations, station.getId(), downLine);
                return;
            }

        }
    }

}
