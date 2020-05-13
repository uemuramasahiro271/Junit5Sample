package app.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import app.main.DBConnecter;
import app.main.SampleDAO;

@ExtendWith(MockitoExtension.class)  // JUnit5を利用時は付ける
public class SampleDAOTest {

	@Mock
	DBConnecter connecter;

	@InjectMocks
	SampleDAO sampleDAO;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testcase1() {
		doReturn("override result by when()").when(connecter).getConnecter();
		sampleDAO.select();
		assertTrue(true);
	}

}
