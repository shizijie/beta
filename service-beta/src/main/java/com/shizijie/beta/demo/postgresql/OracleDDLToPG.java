package com.shizijie.beta.demo.postgresql;

import java.io.*;

public class OracleDDLToPG {

    public static String inFilePath="\\getddl";

    public static String outFilePath="\\getddl\\ddl";

    public static void main(String[] args) throws IOException {
        File file=new File(inFilePath);
        File[] fileArr=file.listFiles();
        for(File f:fileArr){
            if("sql".equals(f.getName().substring(f.getName().lastIndexOf(".")+1))){
                FileInputStream inputStream=new FileInputStream(f);
                InputStreamReader reader=new InputStreamReader(inputStream,"UTF-8");
                BufferedReader br=new BufferedReader(reader);
                StringBuffer bf=new StringBuffer();
                String line="";
                String fileName="";
                String key="KEY_IS_ERROR";
                String primary="";
                int i=0;
                boolean isEnd=false;
                boolean isPass=false;
                while ((line=br.readLine())!=null){
                    line=line.replace("/n","");
                    if(!isEnd){
                        if(line.indexOf("-- Grant/Revoke")!=-1||line.indexOf("-- Create/Recreate")!=-1){
                            isEnd=true;
                        }else if(line.indexOf("tablespace")!=-1){
                            isPass=true;
                        }else if(isPass&&line.indexOf(");")==-1){

                        }else if(isPass&&line.indexOf(");")!=-1){
                            bf.append(";");
                            bf.append(System.getProperty("line.separator"));
                            isPass=false;
                        }else{
                            if(line.indexOf("create table")!=-1){
                                fileName=line.substring(line.indexOf("table")+6).replace("/n","n").replace("/t","");
                                bf.append(line.replace(fileName,"\"public\".\""+fileName+"\""));
                                bf.append(System.getProperty("line.separator"));
                            }else{
                                if(line.length()>1&&" ".equals(line.substring(0,1))){
                                    String content=line.substring(1);
                                    while (" ".equals(content.substring(0,1))){
                                        content=content.substring(1);
                                    }
                                    String kaitou=content.substring(0,content.indexOf(" "));
                                    if("is".equals(kaitou)){
                                        bf.append(line);
                                        bf.append(System.getProperty("line.separator"));
                                    }else{
                                        String typeStr=line.substring(line.indexOf(kaitou)+kaitou.length());
                                        typeStr=typeStr.replace("VARCHAR2","VARCHAR")
                                                .replace("NUMBER","numeric")
                                                .replace("sysate","current_timestamp(0)");
                                        String newStr=" \""+kaitou+"\""+typeStr;
                                        if(!",".equals(newStr.substring(newStr.length()-1))&&newStr.indexOf(";")==-1){
                                            newStr+="$,";
                                            bf.append(newStr);
                                            bf.append(System.getProperty("line.separator"));
                                            String str=" CONSTRAINT \""+fileName+"_pk\" PRIMARY KEY (\""+key+"\")";
                                            bf.append(str+"$");
                                            bf.append(System.getProperty("line.separator"));
                                        }else{
                                            bf.append(newStr);
                                            bf.append(System.getProperty("line.separator"));
                                        }
                                    }
                                }else{
                                    if(line.indexOf("comment on table ")!=-1){
                                        bf.append("comment on table"+"\"public\".\""+fileName+"\"");
                                        bf.append(System.getProperty("line.separator"));
                                    }else if(line.indexOf("comment on column ")!=-1){
                                        bf.append("comment on column "+"\"public\".\""+fileName+"\".\""+line.substring(line.lastIndexOf(".")+1)+"\"");
                                        bf.append(System.getProperty("line.separator"));
                                    }else{
                                        bf.append(line);
                                        bf.append(System.getProperty("line.separator"));
                                    }
                                }
                            }
                        }

                    }else{
                        if(line.indexOf("primary key")!=-1&&line.indexOf("(")!=-1&&i==0){
                            primary=line.substring(line.indexOf("(")+1,line.indexOf(")"));
                            i++;
                        }
                        if(line.indexOf("create index")!=-1){
                            bf.append(line);
                            if(line.indexOf("-")==-1){
                                bf.append(";");
                            }
                            bf.append(System.getProperty("line.separator"));
                        }
                    }
                }
                fileName=fileName.toLowerCase();
                String newFile=outFilePath+"\\"+fileName+".sql";
                File file2=new File(newFile);
                if(!file2.exists()){
                    file2.createNewFile();
                }
                Writer out=new FileWriter(file2);
                String outStr=bf.toString().replace("Create/Recreate indexs","Create indexs");
                if(i==0){
                    outStr=bf.toString().substring(0,bf.toString().indexOf("$"))+bf.toString().substring(bf.toString().lastIndexOf("$")+1);
                }else{
                    outStr=outStr.replace(key,primary).replace("$","");
                }
                out.write(outStr.toLowerCase());
                out.close();
            }
        }
    }
}
