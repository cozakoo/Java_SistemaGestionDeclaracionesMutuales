/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.mycompany.mutuales;

import java.sql.Connection;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author dgc06
 */
public class CheckeoTest{
    
    public CheckeoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of testDB method, of class CheckeoTest.
     */
    @Test
    public void ConectionCorrectaTest() throws Exception {
        System.out.println("TEST CONEXION SUCCESS DATABASE");
        DataBase db = DataBase.getInstance(true);
        db.inicializar("server");
        boolean result = null != db.getConnection();
        assertEquals(true, result);
//        System.out.println("testDB");
//        CheckeoTest instance = new CheckeoTest();
//        instance.testDB();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }
    
}
