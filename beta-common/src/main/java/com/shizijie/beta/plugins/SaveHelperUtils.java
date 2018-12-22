package com.shizijie.beta.plugins;

import org.apache.ibatis.executor.statement.StatementHandler;

import java.util.Stack;

/**
 * @author shizijie
 * @version 2018-12-15 下午7:41
 */
public class SaveHelperUtils {
    private static String NOW_TIME="now(6)";

    public static String getNewSql(String oldSql, String username, String type, SaveHelper.ColumnBean columnBean) {
        if("insert".equalsIgnoreCase(type)){
            if(isBatchInsert(oldSql)){
                return batchInsertSql(oldSql,username,columnBean);
            }else{
                return insertSql(oldSql,username,columnBean);
            }
        }else if("update".equalsIgnoreCase(type)){
            return updateSql(oldSql,username,columnBean);
        }
        return oldSql;
    }

    private static String updateSql(String oldSql, String username, SaveHelper.ColumnBean columnBean) {
        String[] sqlArr=oldSql.split(" ");
        StringBuilder newSql=new StringBuilder();
        for(String str:sqlArr){
            newSql.append(str);
            if("set".equalsIgnoreCase(str)){
                newSql.append(" "+columnBean.getUpdatedDate()+"="+NOW_TIME+",");
                newSql.append(" "+columnBean.getUpdatedBy()+"='"+username+"',");
            }
            newSql.append(" ");
        }
        return newSql.toString();
    }

    private static String insertSql(String oldSql, String username, SaveHelper.ColumnBean columnBean) {
        String[] dates=oldSql.split("values").length!=2?oldSql.split("VALUES"):oldSql.split("values");
        if(dates.length!=2){
            return oldSql;
        }
        StringBuilder newSql=new StringBuilder();
        newSql.append(replaceHead(dates[0],columnBean));
        newSql.append(" values");
        newSql.append(replaceTail(dates[1],username));
        return newSql.toString();
    }




    private static String batchInsertSql(String oldSql, String username, SaveHelper.ColumnBean columnBean) {
        int count=0;
        String[] sqlArr= oldSql.split(" ");
        StringBuilder newSql=new StringBuilder();
        for(String str:sqlArr){
            if(str.equals("(")||str.indexOf("(")>-1){
                if(count==0){
                    str=replaceHead(str,columnBean);
                }else if(count>=1){
                    str="('"+username+"', "+NOW_TIME+", '"+username+"', "+NOW_TIME+str.substring(1,str.length());
                }
                count++;
            }
            newSql.append(str);
            newSql.append(" ");
        }
        return newSql.toString();
    }

    private static String replaceTail(String sql, String username) {
        StringBuilder stringBuilder=new StringBuilder();
        int count=0;
        char[] datas=sql.toCharArray();
        for(char item:datas){
            if('('==item){
                if(count==0){
                    stringBuilder.append(" ('"+username+"',"+NOW_TIME+",'"+username+"',"+NOW_TIME+",");
                    count++;
                    continue;
                }
                count++;
            }
            if(')'==item){
                count--;
            }
            stringBuilder.append(item);
        }
        return stringBuilder.toString();
    }

    private static String replaceHead(String sql,SaveHelper.ColumnBean columnBean){
        return sql.replace("(","("+columnBean.getCreatedBy()+", "
                +columnBean.getCreatedDate()+", "
                +columnBean.getUpdatedBy()+", "
                +columnBean.getUpdatedDate()+", ");
    }

    private static boolean isBatchInsert(String oldSql) {
        String lowSql=oldSql.toLowerCase();
        String subSql=lowSql.substring(lowSql.indexOf("values"));
        int p=locationStrPosition(subSql,"(",")",1);
        if(subSql.length()>p+2){
            return true;
        }
        return false;
    }

    private static int locationStrPosition(String subSql, String start, String end, int num) {
        Stack<Integer> stack=new Stack<>();
        int position=0;
        int i=1000;
        while (i-->0){
          int s=subSql.indexOf(start);
          int e=subSql.indexOf(end);
          boolean flag=false;
          if(e<s||s<0){
              if(stack.isEmpty()){
                  break;
              }
              stack.pop();
              flag=true;
          }else{
              stack.push(s);
          }
          if(flag&&stack.size()<num){
              position+=e;
              break;
          }
          if(flag){
              subSql=subSql.substring(e+1);
              position+=e+1;
          }else{
              subSql=subSql.substring(s+1);
              position+=s+1;
          }
          if(subSql.length()<=0){
              break;
          }
        }
        return position;
    }
}
