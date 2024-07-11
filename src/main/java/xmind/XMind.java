package xmind;

import lombok.Getter;
import lombok.Setter;
import sheet.Sheet;

import java.util.HashMap;
import java.util.UUID;


@Getter
@Setter
public class XMind {

    private String name;
    private HashMap<UUID,Sheet> sheets;
    public XMind() {
        this.sheets = new HashMap<>();
    }


}
