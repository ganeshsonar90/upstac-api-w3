package org.upgrad.upstac.testrequests;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.web.server.ResponseStatusException;
import org.upgrad.upstac.testrequests.lab.CreateLabResult;
import org.upgrad.upstac.testrequests.lab.LabRequestController;
import org.upgrad.upstac.testrequests.lab.LabResult;
import org.upgrad.upstac.testrequests.lab.TestStatus;
import org.upgrad.upstac.users.User;
import org.upgrad.upstac.users.models.Gender;

import java.time.LocalDate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Slf4j
class LabRequestControllerTest {


    @Autowired
    LabRequestController labRequestController;




    @Autowired
    TestRequestQueryService testRequestQueryService;


    @Test
    @WithUserDetails(value = "tester")
    public void calling_assignForLabTest_with_valid_test_request_id_should_update_the_request_status(){

        TestRequest testRequest = getTestRequestByStatus(RequestStatus.INITIATED);
        //Implement this method


        //Create another object of the TestRequest method and explicitly assign this object for Lab Test using assignForLabTest() method
        // from labRequestController class. Pass the request id of testRequest object.
        TestRequest testRequest1 = labRequestController.assignForLabTest(testRequest.requestId);



        //Use assertThat() methods to perform the following two comparisons
        //  1. the request ids of both the objects created should be same
        //  2. the status of the second object should be equal to 'INITIATED'

        assertEquals(testRequest.requestId,testRequest1.requestId);
        assertEquals(testRequest.getStatus(),RequestStatus.INITIATED);


        // make use of assertNotNull() method to make sure that the lab result of second object is not null
        // use getLabResult() method to get the lab result

        assertNotNull(testRequest1.getLabResult());


    }

    public TestRequest getTestRequestByStatus(RequestStatus status) {
        return testRequestQueryService.findBy(status).stream().findFirst().
                orElse(getMockedResponseFrom(createTestRequest()));
    }

    @Test
    @WithUserDetails(value = "tester")
    public void calling_assignForLabTest_with_valid_test_request_id_should_throw_exception(){

        Long InvalidRequestId= -34L;

        //Implement this method


        // Create an object of ResponseStatusException . Use assertThrows() method and pass assignForLabTest() method
        // of labRequestController with InvalidRequestId as Id

        ResponseStatusException result = assertThrows(ResponseStatusException.class,()->{

            TestRequest testRequest = labRequestController.assignForLabTest(InvalidRequestId);
        });


        //Use assertThat() method to perform the following comparison
        //  the exception message should be contain the string "Invalid ID"

        assertThat(result.getMessage(),containsString("Invalid ID"));




    }

    @Test
    @WithUserDetails(value = "tester")
    public void calling_updateLabTest_with_valid_test_request_id_should_update_the_request_status_and_update_test_request_details(){

        TestRequest testRequest = getTestRequestByStatus(RequestStatus.LAB_TEST_IN_PROGRESS);

        //Implement this method
        //Create an object of CreateLabResult and call getCreateLabResult() to create the object. Pass the above created object as the parameter

        CreateLabResult createLabResult=getCreateLabResult(testRequest);

        //Create another object of the TestRequest method and explicitly update the status of this object
        // to be 'LAB_TEST_IN_PROGRESS'. Make use of updateLabTest() method from labRequestController class (Pass the previously created two objects as parameters)

        TestRequest testRequest1 = labRequestController.updateLabTest(testRequest.requestId, createLabResult);
        testRequest1.setStatus(RequestStatus.LAB_TEST_IN_PROGRESS);

        //Use assertThat() methods to perform the following three comparisons
        //  1. the request ids of both the objects created should be same
        //  2. the status of the second object should be equal to 'LAB_TEST_COMPLETED'
        // 3. the results of both the objects created should be same. Make use of getLabResult() method to get the results.

        assertEquals(testRequest.requestId,testRequest1.requestId);
        assertEquals(testRequest1.getStatus(),RequestStatus.LAB_TEST_COMPLETED);
        assertEquals(testRequest.getLabResult(),testRequest1.getLabResult());





    }


    @Test
    @WithUserDetails(value = "tester")
    public void calling_updateLabTest_with_invalid_test_request_id_should_throw_exception(){


        Long InvalidRequestId= -34L;

        TestRequest testRequest = getTestRequestByStatus(RequestStatus.LAB_TEST_IN_PROGRESS);


        //Implement this method

        //Create an object of CreateLabResult and call getCreateLabResult() to create the object. Pass the above created object as the parameter
        CreateLabResult createLabResult=getCreateLabResult(testRequest);



        // Create an object of ResponseStatusException . Use assertThrows() method and pass updateLabTest() method
        // of labRequestController with a negative long value as Id and the above created object as second parameter
        //Refer to the TestRequestControllerTest to check how to use assertThrows() method

        ResponseStatusException result = assertThrows(ResponseStatusException.class,()->{

            labRequestController.updateLabTest(InvalidRequestId,createLabResult);
        });


        //Use assertThat() method to perform the following comparison
        //  the exception message should be contain the string "Invalid ID"

        assertThat(result.getMessage(),containsString("Invalid ID"));


    }

    @Test
    @WithUserDetails(value = "tester")
    public void calling_updateLabTest_with_invalid_empty_status_should_throw_exception(){

        TestRequest testRequest = getTestRequestByStatus(RequestStatus.LAB_TEST_IN_PROGRESS);

        //Implement this method

        //Create an object of CreateLabResult and call getCreateLabResult() to create the object. Pass the above created object as the parameter
        // Set the result of the above created object to null.

        CreateLabResult createLabResult=getCreateLabResult(testRequest);
        createLabResult.setResult(null);


        // Create an object of ResponseStatusException . Use assertThrows() method and pass updateLabTest() method
        // of labRequestController with request Id of the testRequest object and the above created object as second parameter
        //Refer to the TestRequestControllerTest to check how to use assertThrows() method


        ResponseStatusException result = assertThrows(ResponseStatusException.class,()->{

            labRequestController.updateLabTest(testRequest.requestId,createLabResult);
        });

        //Use assertThat() method to perform the following comparison
        //  the exception message should be contain the string "ConstraintViolationException"

        assertThat(result.getMessage(),containsString("ConstraintViolationException"));


    }

    public CreateLabResult getCreateLabResult(TestRequest testRequest) {

        //Create an object of CreateLabResult and set all the values
        // Return the object

       // LabResult labResult = testRequest.getLabResult();

        CreateLabResult createLabResult = new CreateLabResult();
        createLabResult.setBloodPressure("labResult.getBloodPressure()");
        createLabResult.setComments("labResult.getComments()");
        createLabResult.setHeartBeat("labResult.getHeartBeat()");
        createLabResult.setOxygenLevel("labResult.getOxygenLevel()");
        createLabResult.setTemperature("labResult.getTemperature()");
        createLabResult.setResult(TestStatus.POSITIVE);


        return createLabResult; // Replace this line with your code
    }

    public CreateTestRequest createTestRequest() {
        CreateTestRequest createTestRequest = new CreateTestRequest();
        createTestRequest.setAddress("some Addres");
        createTestRequest.setAge(98);
        createTestRequest.setEmail("someone" + "123456789" + "@somedomain.com");
        createTestRequest.setGender(Gender.MALE);
        createTestRequest.setName("someuser");
        createTestRequest.setPhoneNumber("123456789");
        createTestRequest.setPinCode(716768);
        return createTestRequest;
    }
    public TestRequest getMockedResponseFrom(CreateTestRequest createTestRequest) {
        TestRequest testRequest = new TestRequest();

        testRequest.setName(createTestRequest.getName());
        testRequest.setCreated(LocalDate.now());
        testRequest.setStatus(RequestStatus.INITIATED);
        testRequest.setAge(createTestRequest.getAge());
        testRequest.setEmail(createTestRequest.getEmail());
        testRequest.setPhoneNumber(createTestRequest.getPhoneNumber());
        testRequest.setPinCode(createTestRequest.getPinCode());
        testRequest.setAddress(createTestRequest.getAddress());
        testRequest.setGender(createTestRequest.getGender());

        testRequest.setCreatedBy(createUser());

        return testRequest;
    }

    private User createUser() {
        User user = new User();
        user.setId(1L);
        user.setUserName("someuser");
        return user;
    }
}