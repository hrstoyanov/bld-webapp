package peruncs.utilities;

import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

import static peruncs.utilities.ValidationUtils.isNotEmptyOrNull;

public interface HTMX {
     interface RequestHeaders{
        String HX_BOOSTED = "HX-Boosted";
        String HX_CURRENT_URL="HX-Current-URL";
        String HX_HISTORY_RESTORE_REQUEST = "HX-History-Restore-Request";
        String HX_PROMPT="HX-Prompt";
        String HX_REQUEST ="HX-Request";
        String HX_TARGET = "HX-Target";
        String HX_TRIGGER_NAME = "HX-Trigger-Name";
        String HX_TRIGGER = "HX-Trigger";
    }
    interface ResponseHeaders{
        String HX_LOCATION ="HX-Location";
        String HX_PUSH_URL = "HX-Push-Url";
        String HX_REDIRECT = "HX-Redirect";
        String HX_REFRESH = "HX-Refresh";
        String HX_REPLACE_URL = "HX-Replace-Url";
        String HX_RESWAP = "HX-Reswap";
        String HX_RETARGET = "HX-Retarget";
        String HX_RESELECT = "HX-Reselect";
        String HX_TRIGGER = "HX-Trigger";
        String HX_TRIGGER_AFTER_SETTLE ="HX-Trigger-After-Settle";
        String HX_TRIGGER_AFTER_SWAP ="HX-Trigger-After-Swap";
    }

    static SingleLineJSONBuilder json(){
         return new SingleLineJSONBuilder();
    }

    final class SingleLineJSONBuilder implements Creator<SingleLineJSONBuilder, String> {
                    private String path;
                    private String target;
                    private String event;
                    final private Map<String,String> customEvents = new HashMap<>();

        public SingleLineJSONBuilder(){}

        public SingleLineJSONBuilder path(String path){
            this.path = path;
            return this;
        }

        public SingleLineJSONBuilder event(String event){
            this.event = event;
            return this;
        }

        public SingleLineJSONBuilder target(String target){
            this.target = target;
            return this;
        }

        public SingleLineJSONBuilder add(String eventName, String value){
            customEvents.put(eventName, value);
            return this;
        }

        @Override
        public String create(){
            var stringJoiner = new StringJoiner(",","{","}").setEmptyValue("");
            if (isNotEmptyOrNull(path))
                stringJoiner.add(STR."\"path\": \"\{path}\"");
            if(isNotEmptyOrNull(target))
                stringJoiner.add(STR."\"target\": \"\{target}\"");
            if(isNotEmptyOrNull(event))
                stringJoiner.add(STR."\"event\": \"\{event}\"");
            customEvents
                    .entrySet()
                    .stream()
                    .map(ce->STR."\"\{ce.getKey()}\":\"\{ce.getValue()}\"")
                    .forEach(stringJoiner::add);
            return stringJoiner.toString();
        }

        @Override
        public String toString(){
            return create();
        }

        public String str(){
            return create();
        }


    }

}
