package com.dataproviders;

import org.testng.annotations.DataProvider;

public class DataProviders {



    @DataProvider(name="formData")
    public Object[] getFormData(){
        return new Object[][]
                {
                        {"SubmitForm"}
                };
    }

    @DataProvider(name="alerts")
    public Object[] getAlertsData(){
        return new Object[][]
                {
                        {"ALERTCHECK"}
                };
    }

    @DataProvider(name="hovers")
    public Object[] gethoversData(){
        return new Object[][]
                {
                        {"HOVERCHECK"}
                };
    }

    @DataProvider(name="dragdrop")
    public Object[] getDragDropData(){
        return new Object[][]
                {
                        {"DRAGDROP"}
                };
    }


}
