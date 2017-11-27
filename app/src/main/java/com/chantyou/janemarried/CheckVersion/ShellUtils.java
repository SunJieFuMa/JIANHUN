package com.chantyou.janemarried.CheckVersion;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by Administrator on 2016/7/24.
 */

public class ShellUtils {
    public static final String COMMAND_SU = "su";
    public static final String COMMAND_SH = "sh";
    public static final String COMMAND_EXIT = "exit\n";
    public static final String COMMAND_LINE_END = "\n";

    public ShellUtils() {
    }

    public static boolean checkRootPermission() {
        return execCommand("echo root", true, false).result == 0;
    }

    public static ShellUtils.CommandResult execCommand(String command, boolean isRoot) {
        return execCommand(new String[]{command}, isRoot, true);
    }

    public static ShellUtils.CommandResult execCommand(List<String> commands, boolean isRoot) {
        return execCommand(commands == null?null:(String[])commands.toArray(new String[0]), isRoot, true);
    }

    public static ShellUtils.CommandResult execCommand(String[] commands, boolean isRoot) {
        return execCommand(commands, isRoot, true);
    }

    public static ShellUtils.CommandResult execCommand(String command, boolean isRoot, boolean isNeedResultMsg) {
        return execCommand(new String[]{command}, isRoot, isNeedResultMsg);
    }

    public static ShellUtils.CommandResult execCommand(List<String> commands, boolean isRoot, boolean isNeedResultMsg) {
        return execCommand(commands == null?null:(String[])commands.toArray(new String[0]), isRoot, isNeedResultMsg);
    }

    public static ShellUtils.CommandResult execCommand(String[] commands, boolean isRoot, boolean isNeedResultMsg) {
        int result = -1;
        if(commands != null && commands.length != 0) {
            Process process = null;
            BufferedReader successResult = null;
            BufferedReader errorResult = null;
            StringBuilder successMsg = null;
            StringBuilder errorMsg = null;
            DataOutputStream os = null;

            try {
                process = Runtime.getRuntime().exec(isRoot?"su":"sh");
                os = new DataOutputStream(process.getOutputStream());
                String[] var13 = commands;
                int var12 = commands.length;

                String e;
                for(int var11 = 0; var11 < var12; ++var11) {
                    e = var13[var11];
                    if(e != null) {
                        os.write(e.getBytes());
                        os.writeBytes("\n");
                        os.flush();
                    }
                }

                os.writeBytes("exit\n");
                os.flush();
                result = process.waitFor();
                if(isNeedResultMsg) {
                    successMsg = new StringBuilder();
                    errorMsg = new StringBuilder();
                    successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));

                    while((e = successResult.readLine()) != null) {
                        successMsg.append(e);
                    }

                    while((e = errorResult.readLine()) != null) {
                        errorMsg.append(e);
                    }
                }
            } catch (IOException var24) {
                var24.printStackTrace();
            } catch (Exception var25) {
                var25.printStackTrace();
            } finally {
                try {
                    if(os != null) {
                        os.close();
                    }

                    if(successResult != null) {
                        successResult.close();
                    }

                    if(errorResult != null) {
                        errorResult.close();
                    }
                } catch (IOException var23) {
                    var23.printStackTrace();
                }

                if(process != null) {
                    process.destroy();
                }

            }

            return new ShellUtils.CommandResult(result, successMsg == null?null:successMsg.toString(), errorMsg == null?null:errorMsg.toString());
        } else {
            return new ShellUtils.CommandResult(result, (String)null, (String)null);
        }
    }

    public static class CommandResult {
        public int result;
        public String successMsg;
        public String errorMsg;

        public CommandResult(int result) {
            this.result = result;
        }

        public CommandResult(int result, String successMsg, String errorMsg) {
            this.result = result;
            this.successMsg = successMsg;
            this.errorMsg = errorMsg;
        }
    }
}
