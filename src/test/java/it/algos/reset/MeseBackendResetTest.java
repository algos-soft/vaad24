package it.algos.reset;

import it.algos.*;
import it.algos.backend.*;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.*;

/**
 * Project vaad24
 * Created by Algos
 * User: gac
 * Date: Mon, 06-Mar-2023
 * Time: 08:05
 */
@SpringBootTest(classes = {Vaad24SimpleApp.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("reset")
@DisplayName("Mese Reset")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MeseBackendResetTest extends MeseBackendTest {



}
