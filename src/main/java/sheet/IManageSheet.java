package sheet;
import java.util.List;

public interface IManageSheet {

    void duplicateSheet(ISheet sheet);

    void addSheet();

    void removeSheet(ISheet sheet);

    ISheet getSheet(String sheetName);

    List<ISheet> getAllSheets();

    int numberOfSheets();

    ISheet getFirstSheet();

    void removeSheet(int index);

}
