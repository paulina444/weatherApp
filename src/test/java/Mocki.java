import org.example.Api;
import org.example.ProcessCon;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;

public class Mocki {
    Api apiClass;

    @Before
    public void setUp() {
        System.out.println("Run setUp");
        apiClass = Mockito.mock(Api.class);
    }

    @Test
    public void positiveScenario1() {
        Mockito.when(apiClass.connection(anyDouble(), anyDouble(), anyString())).thenReturn("test");
        ProcessCon processCon = new ProcessCon(apiClass);
        String result = processCon.process(0.0, 0.0, "testParam");
        assertThat(result).isEqualTo("TEST");
    }

    @Test
    public void positiveScenario2() {
        Mockito.when(apiClass.connection(anyDouble(), anyDouble(), anyString())).thenReturn("test");
        ProcessCon processCon = new ProcessCon(apiClass);
        String result = processCon.process(1.0, 2.0, "testParam");
        assertThat(result).isEqualTo("TEST");
    }

    @Test
    public void negativeScenario() {
        Mockito.when(apiClass.connection(anyDouble(), anyDouble(), anyString())).thenReturn("");
        ProcessCon processCon = new ProcessCon(apiClass);
        String result = processCon.process(-1.0, 0.0, "błędnyTest");
        assertThat(result).isEqualTo("");
    }
}
