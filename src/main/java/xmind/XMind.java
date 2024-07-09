package xmind;
import config.Configuration;
import config.PropertiesLoader;
import export.*;
import lombok.Getter;
import lombok.Setter;
import sheet.IManageSheet;


@Getter
@Setter
public class XMind {
    private String name;

    private IExportService manageExport;
    private IManageSheet manageSheet;

    public XMind() {
        Configuration configuration = new Configuration(PropertiesLoader.load());
        this.name = configuration.getDefaultXMindName();
    }

    public void setSheetService(IManageSheet sheetService) {
        this.manageSheet = sheetService;
    }

    public void setExportService(IExportService exportService) {
        this.manageExport = exportService;
    }


}
