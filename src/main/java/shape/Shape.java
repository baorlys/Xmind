package shape;

import lombok.Getter;
import lombok.Setter;
import point.Point;


@Getter
@Setter
public class Shape {
    private Point center;
    private int width;
    private int height;

    public Shape(int width) {
        this.width = width;
        this.height = 20;
        this.center = new Point(0, 0);
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
