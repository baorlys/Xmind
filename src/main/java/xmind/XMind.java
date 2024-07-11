package xmind;

import lombok.Getter;
import lombok.Setter;
import sheet.Sheet;
import java.util.HashMap;


@Getter
@Setter
public class XMind {

    private String name;
    private HashMap<Integer,Sheet> sheets;
    public XMind() {
        this.sheets = new HashMap<>();
    }


}
