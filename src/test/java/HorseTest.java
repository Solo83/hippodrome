import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyDouble;

public class HorseTest {



    @Test
    public void nullHorseNameReturnedException() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1.0, 2.0));
    }

    @Test
    public void nullHorseNameExceptionMessage() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1.0, 2.0));
        assertEquals("Name cannot be null.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n"})
    public void blankHorseNameReturnedException(String str) {
        assertThrows(IllegalArgumentException.class, () -> new Horse(str, 1.0, 2.0));

    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n"})
    public void blankHorseNameExceptionMessage(String str) {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse(str, 1.0, 2.0));
        assertEquals("Name cannot be blank.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -2, -3})
    public void negative2ndParameterReturnedException(int number) {
        assertThrows(IllegalArgumentException.class, () -> new Horse("Name", number, 2.0));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -2, -3})
    public void negative2ndParameterExceptionMessage(int number) {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse("Name", number, 2.0));
        assertEquals("Speed cannot be negative.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -2, -3})
    public void negative3ndParameterReturnedException(int number) {
        assertThrows(IllegalArgumentException.class, () -> new Horse("Name", 1.0, number));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, -2, -3})
    public void negative3ndParameterExceptionMessage(int number) {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> new Horse("Name", 1.0, number));
        assertEquals("Distance cannot be negative.", exception.getMessage());
    }

    @Test
    public void getName() {
        Horse horse = new Horse("Name", 1.0, 2.0);
        String horseName = horse.getName();
        assertEquals(horseName, "Name");
    }

    @Test
    public void getSpeed() {
        Horse horse = new Horse("Name", 1.0, 2.0);
        Double horseSpeed = horse.getSpeed();
        assertEquals(horseSpeed, 1.0);
    }

    @Test
    public void getDistance() {
        Horse horse = new Horse("Name", 1.0, 2.0);
        Double horseDistance = horse.getDistance();
        assertEquals(horseDistance, 2.0);

        Horse horse2 = new Horse("Name", 1.0);
        Double horseDistance2 = horse2.getDistance();
        assertEquals(horseDistance2, 0);
    }

    @ParameterizedTest
    @ValueSource(doubles = {1.0, 2.0, 3.0})
    public void move(double value) {

        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            Horse horse = new Horse("Name", 31.0, 2.0);
            horse.move();
            horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));

            Mockito.when(Horse.getRandomDouble(anyDouble(), anyDouble())).thenReturn(value);
            double distance = horse.getDistance() + (horse.getSpeed() * Horse.getRandomDouble(anyDouble(), anyDouble()));
            horse.move();
            assertEquals(distance, horse.getDistance());

        }
    }


}
