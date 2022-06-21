import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.only;


public class HippodromeTest {

    @Test
    public void nullHorsesListReturnedException() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
    }

    @Test
    public void nullHorsesListExceptionMessage() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
        assertEquals("Horses cannot be null.", exception.getMessage());
    }

    @Test
    public void blankedListReturnedException() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()));
    }

    @Test
    public void blankedListExceptionMessage() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()));
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    @Test
    public void getHorses() {

        List<Horse> list = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            list.add(new Horse("Name" + i, i));
        }

        Hippodrome hippodrome = new Hippodrome(list);
        assertEquals(list, hippodrome.getHorses());
    }


    @Mock
    List<Horse> mockList = Mockito.spy(new ArrayList<>());


    {
        for (int i = 0; i < 50; i++) {
            mockList.add(Mockito.spy(new Horse("Name" + i, i, 50 - i)));
        }
    }

    Hippodrome mockHippodrome = Mockito.spy(new Hippodrome(mockList));

    @Test
    public void move() {

        mockHippodrome.move();

        for (Horse horse : mockList) {
            Mockito.verify(horse, only()).move();
        }

    }

    @Test
    public void getWinner() {

        Horse max = mockHippodrome.getHorses().stream().max(Comparator.comparingDouble(Horse::getDistance)).get();

        assertEquals(max, mockHippodrome.getWinner());
    }

}
