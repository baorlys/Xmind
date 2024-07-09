package sheet;

import config.Configuration;
import config.PropertiesLoader;
import node.IRootNode;
import node.RootNode;

import java.util.ArrayList;
import java.util.List;

public class ManageSheet implements IManageSheet {

    List<ISheet> sheets = new ArrayList<>();


    public ManageSheet() {
        defaultSheet();
    }
    private void defaultSheet() {
        Configuration configuration = new Configuration(PropertiesLoader.load());
        IRootNode rootTopic = new RootNode(configuration.getDefaultRootTopicName());
        sheets.add(new Sheet(configuration.getDefaultSheetName(), rootTopic));
    }
    @Override
    public void duplicateSheet(ISheet sheet) {
        sheets.add(sheet);
    }

    @Override
    public void addSheet() {
        Configuration configuration = new Configuration(PropertiesLoader.load());
        IRootNode rootTopic = new RootNode(configuration.getDefaultRootTopicName());
        int sheetNumber = sheets.size();
        sheets.add(new Sheet("Map " + sheetNumber, rootTopic));
    }


    @Override
    public void removeSheet(ISheet sheet) {
        sheets.remove(sheet);
    }



    @Override
    public ISheet getSheet(String sheetName) {
        return sheets.stream()
                .filter(s -> s.getName().equals(sheetName))
                .findFirst()
                .orElse(null);
    }


    @Override
    public List<ISheet> getAllSheets() {
        return sheets;
    }

    @Override
    public int numberOfSheets() {
        return sheets.size();
    }

    @Override
    public ISheet getFirstSheet() {
        return sheets.stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public void removeSheet(int index) {
        sheets.remove(index);
    }


}
