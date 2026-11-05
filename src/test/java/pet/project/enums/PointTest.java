package pet.project.enums;

import org.junit.jupiter.api.Test;
import pet.project.exceptions.NextPointAfterAdvantageException;
import pet.project.models.Point;

import static org.junit.jupiter.api.Assertions.*;

public class PointTest {

    private Point point;

    @Test
    public void shouldBeAfterLoveComeFifteen(){
        point = Point.LOVE;
        assertEquals(Point.FIFTEEN, point.next());
    }

    @Test
    public void shouldBeAfterFifteenComeThirty(){
        point = Point.FIFTEEN;
        assertEquals(Point.THIRTY, point.next());
    }

    @Test
    public void shouldBeAfterThirtyComeForty(){
        point = Point.THIRTY;
        assertEquals(Point.FORTY, point.next());
    }

    @Test
    public void shouldBeAfterFortyComeAdvantage(){
        point = Point.FORTY;
        assertEquals(Point.ADVANTAGE, point.next());
    }

    @Test
    public void shouldBeAfterAdvantageComeException(){
        point = Point.ADVANTAGE;
        assertThrows(NextPointAfterAdvantageException.class, () -> {
            point.next();
        });
    }
}
