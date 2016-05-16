package utils;


import by.bsu.chat.Constants;
import by.bsu.chat.Message;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


public class MessageHelper {

    private static final JSONParser jsonParser = new JSONParser();
    public static final String MESSAGE_PART_ALL_MSG = "messages";

    public static final String MESSAGE_PART_TOKEN = "token";
    public static final String TOKEN_TEMPLATE = "TN%dEN";


    public static Message getPostMessage(HttpServletRequest req) throws ParseException, IOException {
        JSONObject jsonObject = stringToJsonObject(req.getReader().readLine());
        String id = ((String) jsonObject.get(Constants.Message.FIELD_ID));
        String author = ((String) jsonObject.get(Constants.Message.FIELD_AUTHOR));
        String photo = ((String) jsonObject.get(Constants.Message.FIELD_PHOTOURL));
        String text = ((String) jsonObject.get(Constants.Message.FIELD_TEXT));
        Message message = new Message();
        message.setId(id);
        message.setAuthor(author);
        message.setText(text);
        message.setMethod(Constants.REQUEST_METHOD_POST);
        message.setPhotoURL(photo);
        return message;
    }

    public static Message getPutMessage(HttpServletRequest req) throws ParseException, IOException {
        JSONObject jsonObject = stringToJsonObject(req.getReader().readLine());
        String id = ((String) jsonObject.get(Constants.Message.FIELD_ID));
        String text = ((String) jsonObject.get(Constants.Message.FIELD_TEXT));
        Message message = new Message();
        message.setId(id);
        message.setText(text);
        message.setMethod(Constants.REQUEST_METHOD_PUT);
        return message;
    }

    public static Message getDelMessage(HttpServletRequest req) throws ParseException, IOException {
        JSONObject jsonObject = stringToJsonObject(req.getReader().readLine());
        String id = ((String) jsonObject.get(Constants.Message.FIELD_ID));
        Message message = new Message();
        message.setId(id);
        message.setMethod(Constants.REQUEST_METHOD_DELETE);
        return message;
    }

    public static JSONObject stringToJsonObject(String json) throws ParseException {
        return JSONObject.class.cast(jsonParser.parse(json.trim()));
    }

    private static JSONObject messageToJSONObject(Message message) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(Constants.Message.FIELD_ID, message.getId());
        jsonObject.put(Constants.Message.FIELD_AUTHOR, message.getAuthor());
        jsonObject.put(Constants.Message.FIELD_TEXT, message.getText());
        jsonObject.put(Constants.Message.FIELD_METHOD, message.getMethod());
        jsonObject.put(Constants.Message.FIELD_PHOTOURL, message.getPhotoURL());

        return jsonObject;
    }

    public static int parseToken(String token) {
        String encodedIndex = token.substring(2, token.length() - 2);
        int stateCode = Integer.valueOf(encodedIndex);
        return decodeIndex(stateCode);
    }

    private static int decodeIndex(int stateCode) {
        return (stateCode - 11) / 8;
    }

    public static String buildServerResponseBody(List<Message> messages, int lastPosition) {
        JSONArray array = getJsonArrayOfMessages(messages);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(MESSAGE_PART_ALL_MSG, array);
        jsonObject.put(MESSAGE_PART_TOKEN, buildToken(lastPosition));
        return jsonObject.toJSONString();
    }

    private static JSONArray getJsonArrayOfMessages(List<Message> messages) {


        List<JSONObject> jsonMessages = new LinkedList<>();
        for (Message message : messages) {
            jsonMessages.add(messageToJSONObject(message));
        }

        JSONArray array = new JSONArray();
        array.addAll(jsonMessages);
        return array;
    }

    public static String buildToken(int receivedMessagesCount) {
        Integer stateCode = encodeIndex(receivedMessagesCount);
        return String.format(TOKEN_TEMPLATE, stateCode);
    }

    private static int encodeIndex(int receivedMessagesCount) {
        return receivedMessagesCount * 8 + 11;
    }

}
