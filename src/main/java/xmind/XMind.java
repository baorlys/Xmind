package xmind;
import config.Configuration;
import config.PropertiesLoader;
import lombok.Getter;
import lombok.Setter;
import sheet.Sheet;
import java.util.HashMap;


@Getter
@Setter
public class XMind {
    private String name;

    private HashMap<Integer,Sheet> sheets;
    private Configuration configuration;


    public XMind() {
        this.sheets = new HashMap<>();
        this.configuration = new Configuration(PropertiesLoader.load());
    }


}
