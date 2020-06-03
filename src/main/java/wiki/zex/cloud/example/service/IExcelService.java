package wiki.zex.cloud.example.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface IExcelService {

    void download(HttpServletResponse httpServletResponse);

    void upload(MultipartFile file);

}
