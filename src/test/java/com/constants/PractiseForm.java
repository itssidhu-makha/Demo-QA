package com.constants;

public interface PractiseForm {
    String ID_FIRSTNAME ="firstName";
    String ID_LASTNAME="lastName";
    String ID_EMAIL ="userEmail";
    String XPATH_GENDER_SELECTION="//*[@id='genterWrapper']//input[@value='Male']";
    String ID_MOBILE="userNumber";
    String ID_DATE_PICKER="dateOfBirthInput";//input
    String ID_SUBJECT_CONTAINER="subjectsContainer";
    String ID_CURRENT_ADDRESS="currentAddress";
    String XPATH_STATE="//div[@id='stateCity-wrapper']/div[2]//input";
    String XPATH_CITY="//div[@id='stateCity-wrapper']/div[3]//input";
    String ID_UPLOAD_PIC="uploadPicture";
    String ID_SUBMIT_BUTTON="submit";
    String CLASS_MONTH_PICKER="react-datepicker__month-select";
    String CLASS_YEAR_PICKER="react-datepicker__year-select";

    String ID_TIMEALERT = "timerAlertButton";
}
