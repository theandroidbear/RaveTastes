package com.ravemaster.recipeapp.apiKey;

public class ApiKey {
    static int currentKeyIndex = 0;
    public static String getApiKey(){
        String apikey = "";
        switch (currentKeyIndex){
            case 0:
                apikey = "ab9a345b1dmsh95573e64e14301dp11f08cjsnbb669399ee87";
                break;
            case 1:
                apikey = "cee03e6aabmsh2fa043ef9ad519ap17e59bjsn695eb9e6b3fb";
                break;
            case 2:
                apikey = "7e3d2f10bdmsh70e6fefa71835adp16c240jsnb41f1b9c1073";
                break;
            case 3:
                apikey = "7a9a8d4846mshcfaa4b403a596e8p1d45b5jsneca71b63bb58";
                break;
            case 4:
                apikey = "9514810a91mshaa1e82f038a194dp192b4djsn80ef1486dc10";
                break;
            case 5:
                apikey = "cf8e818c30mshc3a90bc6ac3c539p10a09bjsn3c104522f764";
                break;
            case 6:
                apikey = "ed4b9641acmshe6e3944254ccdf8p12c249jsn32c2d44e1c9b";
                break;
        }
        currentKeyIndex = (currentKeyIndex + 1) % 7;
        return apikey;
    }
}
