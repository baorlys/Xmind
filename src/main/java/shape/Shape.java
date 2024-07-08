package shape;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Shape {
    private Point center;
    private int width;
    private int height;

    public Shape(int width, Point center) {
        this.width = width;
        this.height = 20;
        this.center = center;
    }


    public boolean isContainPoint(Point point) {
        int x = point.getX();
        int y = point.getY();
        int x1 = center.getX() - width / 2;
        int x2 = center.getX() + width / 2;
        int y1 = center.getY() - height / 2;
        int y2 = center.getY() + height / 2;
        return x >= x1 && x <= x2 && y >= y1 && y <= y2;
    }
}
