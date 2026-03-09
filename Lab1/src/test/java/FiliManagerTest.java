import Models.School;
import FileManager.FileHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class FileManagerMockTest {

    private FileHandler mockHandler;
    private School testSchool;
    private final String TEST_FILE = "test_data.csv";

    @BeforeEach
    void setUp() {
        mockHandler = mock(FileHandler.class);
        testSchool = new School("KPI Lyceum");
    }

    @Test
    void testExport_VerifyInteraction() {
        mockHandler.exportToCSV(testSchool, TEST_FILE);
        verify(mockHandler,times(1)).exportToCSV(testSchool, TEST_FILE);
    }

    @Test
    void testImport_ReturnsMockedData() {
        when(mockHandler.importFromCSV(TEST_FILE)).thenReturn(testSchool);

        School result = mockHandler.importFromCSV(TEST_FILE);

        assertEquals("KPI Lyceum", result.getName());
    }
}