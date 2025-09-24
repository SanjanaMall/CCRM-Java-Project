package edu.ccrm.config;

public class AppConfig {
    private static AppConfig instance;
    private String dataFolderPath;
    private int maxCreditsPerSemester;
    
    private AppConfig() {
        this.dataFolderPath = "data/";
        this.maxCreditsPerSemester = 18;
    }
    
    public static AppConfig getInstance() {
        if (instance == null) {
            synchronized (AppConfig.class) {
                if (instance == null) {
                    instance = new AppConfig();
                }
            }
        }
        return instance;
    }
    
    public String getDataFolderPath() { return dataFolderPath; }
    public void setDataFolderPath(String dataFolderPath) { this.dataFolderPath = dataFolderPath; }
    
    public int getMaxCreditsPerSemester() { return maxCreditsPerSemester; }
    public void setMaxCreditsPerSemester(int maxCreditsPerSemester) { this.maxCreditsPerSemester = maxCreditsPerSemester; }
}