import org.jibble.pircbot.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Mark on 3/24/2015.
 */
public class PaxosBot extends PircBot {
    Map diseaseList;
    Map generalList;
    Map dualParamList;
    Skiddy sk;

    String channel;
    String auth;

    /**
     *
     * @param channel
     * @param auth
     * @param helloMessage
     */
    public PaxosBot(String channel, String auth, String helloMessage){
        this.init();
        this.setName("PaxosBot");
        this.setVerbose(true);
        this.channel = channel;
        this.auth = auth;

        try {

            this.connect("irc.freenode.net", 6667, auth);
            this.joinChannel("#" + channel);
            this.sendMessage("#" + channel, helloMessage);


        }catch(NickAlreadyInUseException e){
            System.out.println("nick already in use");
            e.printStackTrace();
        }catch (IOException e){
            System.out.println("ioexception error");
            e.printStackTrace();
        }catch(IrcException e){
            System.out.println("ircexception error");
            e.printStackTrace();
        }
    }

    /**
     *
     * @param channel
     * @param sender
     * @param login
     * @param hostName
     * @param message
     */
    public void onMessage(String channel, String sender, String login, String hostName, String message){
        Random r = new Random();
        String[] messageArray = message.split(" ");
        String command =  messageArray[0];

        if(command.toLowerCase().equals("!grep")) {
            sendMessage(channel, sender + " do you even grep?");
            if ((r.nextInt(90) + 10 < 45)) {
                kick(channel, sender);
            }
        }else if(generalList.containsKey(command.toLowerCase())){
            String temp = generalList.get(command.toLowerCase()).toString();
            if(temp.contains("*sender*")){
                temp = temp.replace("*sender*", sender);
                sendMessage(channel,temp);
            }else{
                sendMessage(channel, temp);
            }

        }else if(diseaseList.containsKey(command.toLowerCase())){
            if(messageArray.length > 1){
                String temp = diseaseList.get(command.toLowerCase()).toString();
                temp = temp.replace("the irc", messageArray[1]);
                sendMessage(channel, sender + temp);

            }else{
                sendMessage(channel, sender + diseaseList.get(command.toLowerCase()));
            }
        }else if(dualParamList.containsKey(command.toLowerCase())){
            if(messageArray.length > 1){
                if(dualParamList.containsKey(command.toLowerCase() + " " + messageArray[1].toLowerCase()))
                    sendMessage(channel, dualParamList.get(command.toLowerCase() + " " + messageArray[1].toLowerCase()).toString());
            }else{
                sendMessage(channel, dualParamList.get(command.toLowerCase()).toString());
            }
        }
    }

    /**
     *
     * @param sender
     * @param login
     * @param hostname
     * @param message
     */
    @Override
    protected void onPrivateMessage(String sender, String login, String hostname, String message) {
        //super.onPrivateMessage(sender, login, hostname, message);
        String[] messageArray = message.split(" ");
        String command =  messageArray[0];
        String channel = messageArray[1];
        String pass = messageArray[2];

        if(command.toLowerCase().equals("!opme")){
            if(sk.checkUser(sender) && sk.checkUser(pass)){
                op(channel, sender);
            }
        }else if(command.toLowerCase().equals("!deopme")){
            if(sk.checkUser(sender) && sk.checkUser(pass)){
                deOp(channel,sender);
            }
        }

    }

    @Override
    protected  void onDisconnect(){
        try {

            this.connect("irc.freenode.net", 6667, this.auth);
            this.joinChannel("#" + this.channel);
            this.sendMessage("#" + this.channel, "I'm Back!");


        }catch(NickAlreadyInUseException e){
            System.out.println("nick already in use");
            e.printStackTrace();
        }catch (IOException e){
            System.out.println("ioexception error");
            e.printStackTrace();
        }catch(IrcException e){
            System.out.println("ircexception error");
            e.printStackTrace();
        }
    }

    /**
     *
     * @param channel
     * @param sender
     * @param login
     * @param hostname
     */
    public void onJoin(String channel, String sender, String login, String hostname){
        if(sk.checkUser(sender)){
            op(channel, sender);
        }
    }

    /**
     *
     */
    private void init(){
        diseaseList = new HashMap<String, String>();
        generalList = new HashMap<String, String>();
        dualParamList = new HashMap<String, String>();

        sk = new Skiddy();

        /**
         *Initialize the disease list
         */
        diseaseList.put("!ebola", " is trying to infect the irc with ebola.");
        diseaseList.put("!aids", ", that is not a politically correct thing to talk about.");

        /**
         *Initialize general command list
         */
        generalList.put("!help", "Command List: !help");
        generalList.put("!goldenrule", "Don't bang crazy.");
        generalList.put("!shiny","Find Glorious_Coffee a \"Kaylee.\"");
        generalList.put("!penis", "get hard");
        //generalList.put("!grep","*sender* do you even grep?");

        /**
         * Initalize the double param list
         */
        dualParamList.put("!permit", "There is currently one permit available for request: \"idle\". Type \"!permit idle\" to make a request.");
        dualParamList.put("!permit idle", "To request an idle permit, a 15074 permit is required.");
        dualParamList.put("!permit 15074", "To request a 15074 permit, a firefly permit is required.");
        dualParamList.put("!permit firefly", "To request a firefly permit, an idle permit is required.");


    }


    public String getRandomNick(){
        String sReturn = "";



        return sReturn;
    }

}
