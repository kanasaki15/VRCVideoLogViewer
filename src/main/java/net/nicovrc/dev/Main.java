package net.nicovrc.dev;

import com.amihaiemil.eoyaml.Yaml;
import com.amihaiemil.eoyaml.YamlMapping;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

    private static SimpleDateFormat file_sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
    private static SimpleDateFormat log_sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        System.out.println("[Info] VRCVideoLogViewer Ver " + Function.Version + "起動");

        File file = new File("./config.yml");

        final String configText = """
                # VRChat ログフォルダパス (VRChat log folder path)
                logfolder: ''
                # デバッグログを表示するか (Enable debug log display?)
                debugOutput: true
                # 過去のログから取得して表示するか (Display data from previous logs?)
                oldLogCheck: true
                # 動画プレーヤーのログを表示するか (Enable video player log display?)
                VideoPlayer: true
                # ImageDownloaderのログを表示するか (Enable ImageDownloader log display?)
                ImageDownloader: true
                # StringDownloaderのログを表示するか (Enable StringDownloader log display?)
                StringDownloader: true
                """;

        System.out.println("[Info] config.yml 存在チェック");
        if (!file.exists()){
            try {
                FileWriter file1 = new FileWriter("./config.yml");
                PrintWriter pw = new PrintWriter(new BufferedWriter(file1));
                pw.print(configText);
                pw.close();
                file1.close();
                pw = null;
                file1 = null;
            } catch (Exception e){
                e.printStackTrace();
            }
            System.out.println("config.ymlを設定してください。\nPlease configure the config.yml file.");
            return;
        }


        String temp_logFolderPass = null;
        boolean temp_debugOutput = false;
        boolean temp_oldLogCheck = false;
        boolean temp_VideoPlayer = false;
        boolean temp_ImageDownloader = false;
        boolean temp_StringDownloader = false;
        try {
            final YamlMapping yamlMapping = Yaml.createYamlInput(new File("./config.yml")).readYamlMapping();
            temp_logFolderPass = yamlMapping.string("logfolder");
            temp_debugOutput = yamlMapping.bool("debugOutput");
            temp_oldLogCheck = yamlMapping.bool("oldLogCheck");
            temp_VideoPlayer = yamlMapping.bool("VideoPlayer");
            temp_ImageDownloader = yamlMapping.bool("ImageDownloader");
            temp_StringDownloader = yamlMapping.bool("StringDownloader");
        } catch (Exception e){
            // e.printStackTrace();

        }

        final String logFolderPass = temp_logFolderPass;
        final boolean debugOutput = temp_debugOutput;
        final boolean oldLogCheck = temp_oldLogCheck;
        final boolean VideoPlayer = temp_VideoPlayer;
        final boolean ImageDownloader = temp_ImageDownloader;
        final boolean StringDownloader = temp_StringDownloader;

        if (debugOutput){
            System.out.println("[Info] フォルダチェック");
        }
        if (logFolderPass != null){
            file = new File(logFolderPass);
            if (!file.exists()){
                System.out.println("フォルダが見つかりませんでした。\nFolder not found.");
                return;
            }
        } else {
            System.out.println("設定ファイルが正しく設定されていません。\nThe configuration file is not set up correctly.");
            return;
        }

        List<String> logFileList = new ArrayList<>();
        try {
            for (File f : file.listFiles()){
                if (f.getName().startsWith("output_log_")){
                    logFileList.add(f.getName());
                }
            }
        } catch (Exception e){
            // e.printStackTrace();
        }
        if (logFileList.isEmpty()){
            System.out.println("ログファイルが見つかりませんでした。\nLog file not found.");
            return;
        }

        if (debugOutput){
            System.out.println("[Info] ログファイルの並び替え");
        }
        if (logFileList.size() > 1){
            try {

                List<String> temp = new ArrayList<>();

                String[] temp1 = new String[logFileList.size()];
                long[] temp2 = new long[logFileList.size()];
                int i = 0;
                for (String s : logFileList) {
                    Date date = file_sdf.parse(s.replaceAll("output_log_", "").replaceAll("\\.txt", ""));
                    temp1[i] = s;
                    temp2[i] = date.getTime();
                    i++;
                }

                boolean isMove = true;
                String te1 = "";
                long te2 = -1;

                while (isMove){
                    isMove = false;
                    for (i = 0; i < temp2.length; i++){
                        if (i + 1 < temp2.length){
                            if (temp2[i] >= temp2[i + 1]){
                                isMove = true;
                                te1 = temp1[i];
                                te2 = temp2[i];

                                temp1[i] = temp1[i + 1];
                                temp2[i] = temp2[i + 1];
                                temp1[i + 1] = te1;
                                temp2[i + 1] = te2;
                            }
                        }
                    }
                }

                for (i = 0; i < temp1.length; i++){
                    temp.add(temp1[i]);
                }
                logFileList = temp;

            } catch (Exception e){
                //e.printStackTrace();
                if (debugOutput){
                    System.out.println("[Error] 並び替えに失敗");
                }
            }
        }


        final LogData lastLogData = new LogData();
        lastLogData.setLogDate(new Date());

        if (oldLogCheck){
            if (debugOutput){
                System.out.println("[Info] 抽出開始");
            }

            for (String s : logFileList) {
                file = new File(logFolderPass + "\\" + s);

                String text = Function.getTextForFile(file);
                try {
                    List<LogData> log = Function.getLogForURL(text);
                    for (LogData logData : log) {
                        if (VideoPlayer && logData.getURLType().equals("Video")){
                            System.out.println("["+log_sdf.format(logData.getLogDate())+"] " + logData.getURL() + " ("+logData.getURLType()+")");
                        } else if (ImageDownloader && logData.getURLType().equals("Image")){
                            System.out.println("["+log_sdf.format(logData.getLogDate())+"] " + logData.getURL() + " ("+logData.getURLType()+")");
                        } else if (StringDownloader && logData.getURLType().equals("String")){
                            System.out.println("["+log_sdf.format(logData.getLogDate())+"] " + logData.getURL() + " ("+logData.getURLType()+")");
                        }

                        lastLogData.setLogDate(logData.getLogDate());
                        lastLogData.setURL(logData.getURL());
                        lastLogData.setErrorMessage(logData.getErrorMessage());
                        lastLogData.setURLType(logData.getURLType());
                    }

                } catch (Exception e){
                    //e.printStackTrace();
                    if (debugOutput){
                        System.out.println("[Error] ログファイル読み込みに失敗");
                    }
                }
            }
        }


        if (debugOutput){
            System.out.println(log_sdf.format(lastLogData.getLogDate()));
            System.out.println("[Info] リアルタイム取得開始します...");
        }
        Timer timer = new Timer();
        final String lastLogFile = logFolderPass + "\\" + logFileList.getLast();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    File f = new File(lastLogFile);
                    for (LogData logData : Function.getLogForURL(Function.getTextForFile(f))){
                        if (logData.getLogDate().getTime() > lastLogData.getLogDate().getTime()){

                            if (VideoPlayer && logData.getURLType().equals("Video")){
                                System.out.println("["+log_sdf.format(logData.getLogDate())+"] " + logData.getURL() + " ("+logData.getURLType()+")");
                            } else if (ImageDownloader && logData.getURLType().equals("Image")){
                                System.out.println("["+log_sdf.format(logData.getLogDate())+"] " + logData.getURL() + " ("+logData.getURLType()+")");
                            } else if (StringDownloader && logData.getURLType().equals("String")){
                                System.out.println("["+log_sdf.format(logData.getLogDate())+"] " + logData.getURL() + " ("+logData.getURLType()+")");
                            }

                            lastLogData.setLogDate(logData.getLogDate());
                            lastLogData.setURL(logData.getURL());
                            lastLogData.setErrorMessage(logData.getErrorMessage());
                            lastLogData.setURLType(logData.getURLType());
                        }
                    }
                } catch (Exception e){
                    timer.cancel();
                }
            }
        }, 0L, 1000L);

        //timer.cancel();

    }
}