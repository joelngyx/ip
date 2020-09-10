public class DukeExceptions {
    public static String checkErrorType(String s){
        String temp = s.replace("todo", "");
        temp = temp.replace("deadline", "");
        temp = temp.replace("event", "");
        temp = temp.replace("/", "");
        temp = temp.replace(" ", "");
        if(s.contains("todo")){
            if(temp.equals("")){
                return "Todo_Empty_Input";
            }
        }
        if(s.contains("event")){
            if(!s.contains("/")){
                return "Event_Lacks_Slash";
            } else if(temp.equals("")){
                return "Event_Empty_Input";
            }
        }
        if(s.contains("deadline")) {
            if (!s.contains("/")) {
                return "Deadline_Lacks_Slash";
            } else if (temp.equals("")) {
                return "Deadline_Empty_Input";
            }
        }
        return "Error";
    }
}
