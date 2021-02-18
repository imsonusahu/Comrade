package com.comrade.comrade.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.comrade.comrade.R;
import com.comrade.comrade.SessionManager.QueryPreferences;
import com.comrade.comrade.adapters.MessageAdapter;
import com.comrade.comrade.chats.MessageFormat;
import com.comrade.comrade.databinding.ActivityChatBinding;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ChatActivity extends AppCompatActivity {


    ActivityChatBinding binding;

    public static final String TAG = "ChatActivity";
    public static String uniqueId;

    private String Username;
    private Boolean hasConnection = false;

    private ListView messageListView;
    private MessageAdapter messageAdapter;

    private Thread thread2;
    private boolean startTyping = false;
    private int time = 2;

    private Socket mSocket;
    private HashMap<String, String> user;
    private String roomId,userMatchId,myId;


    {
        try {
            mSocket = IO.socket("http://comrade.live/");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("HandlerLeak")
    Handler handler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.i(TAG, "handleMessage: typing stopped " + startTyping);
            if (time == 0) {
                setTitle("SocketIO");
                Log.i(TAG, "handleMessage: typing stopped time is " + time);
                startTyping = false;
                time = 2;
            }

        }
    };

    QueryPreferences queryPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat);

        queryPreferences=new QueryPreferences(this);
        user=queryPreferences.getUserDetail();
        Username = user.get(queryPreferences.name);
        myId = user.get(queryPreferences.uid);

        Bundle bundle = getIntent().getExtras();

        roomId = bundle.getString("roomId");
        userMatchId = bundle.getString("userMatchId");


        uniqueId = UUID.randomUUID().toString();
        Log.i(TAG, "onCreate: " + uniqueId);

        if (savedInstanceState != null) {
            hasConnection = savedInstanceState.getBoolean("hasConnection");
        }


        if (!hasConnection) {


                mSocket.connect();
                mSocket.on("connect user", onNewUser);
                mSocket.on("chat message", onNewMessage);
                mSocket.on("on typing", onTyping);

                JSONObject userId = new JSONObject();
                try {
                    userId.put("name", Username);
                    userId.put("room_id", userMatchId);
                    userId.put("sender_user", myId);

                    mSocket.emit("connect user", userId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else {

                Toast.makeText(getApplicationContext(), "Room id doesn't match", Toast.LENGTH_SHORT).show();

                onBackPressed();
            }



        Log.i(TAG, "onCreate: " + hasConnection);
        hasConnection = true;


        Log.i(TAG, "onCreate: " + Username + " " + "Connected");


        messageListView = findViewById(R.id.chat_rv);

        List<MessageFormat> messageFormatList = new ArrayList<>();
        messageAdapter = new MessageAdapter(this, R.layout.item_message, messageFormatList);
        messageListView.setAdapter(messageAdapter);

        onTypeButtonEnable();
    }

    @Override
    protected void onSaveInstanceState(@NotNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("hasConnection", hasConnection);
    }

    public void onTypeButtonEnable() {
        binding.textField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                JSONObject onTyping = new JSONObject();
                try {
                    onTyping.put("typing", true);
                    onTyping.put("username", Username);
                    onTyping.put("uniqueId", uniqueId);
                    mSocket.emit("on typing", onTyping);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (charSequence.toString().trim().length() > 0) {
                    binding.sendButton.setEnabled(true);
                } else {
                    binding.sendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i(TAG, "run: ");
                    Log.i(TAG, "run: " + args.length);
                    JSONObject data = (JSONObject) args[0];
                    String username;
                    String message;
                    String id;
                    try {
                        username = data.getString("username");
                        message = data.getString("message");
                        id = data.getString("uniqueId");

                        Log.i(TAG, "run: " + username + message + id);

                        MessageFormat format = new MessageFormat(id, username, message);
                        Log.i(TAG, "run:4 ");
                        messageAdapter.add(format);
                        Log.i(TAG, "run:5 ");

                    } catch (Exception e) {
                        return;
                    }
                }
            });
        }
    };

    Emitter.Listener onNewUser = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    int length = args.length;

                    if (length == 0) {
                        return;
                    }
                    //Here i'm getting weird error..................///////run :1 and run: 0
                    Log.i(TAG, "run: ");
                    Log.i(TAG, "run: " + args.length);
                    String username = args[0].toString();
                    try {
                        JSONObject object = new JSONObject(username);
                        username = object.getString("username");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    MessageFormat format = new MessageFormat(null, username, null);
                    messageAdapter.add(format);
                    messageListView.smoothScrollToPosition(0);
                    messageListView.scrollTo(0, messageAdapter.getCount() - 1);
                    Log.i(TAG, "run: " + username);
                }
            });
        }
    };


    Emitter.Listener onTyping = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    Log.i(TAG, "run: " + args[0]);
                    try {
                        Boolean typingOrNot = data.getBoolean("typing");
                        String userName = data.getString("username") + " is Typing......";
                        String id = data.getString("uniqueId");

                        if (id.equals(uniqueId)) {
                            typingOrNot = false;
                        } else {
                            setTitle(userName);
                        }

                        if (typingOrNot) {

                            if (!startTyping) {
                                startTyping = true;
                                thread2 = new Thread(
                                        new Runnable() {
                                            @Override
                                            public void run() {
                                                while (time > 0) {
                                                    synchronized (this) {
                                                        try {
                                                            wait(1000);
                                                            Log.i(TAG, "run: typing " + time);
                                                        } catch (InterruptedException e) {
                                                            e.printStackTrace();
                                                        }
                                                        time--;
                                                    }
                                                    handler2.sendEmptyMessage(0);
                                                }

                                            }
                                        }
                                );
                                thread2.start();
                            } else {
                                time = 2;
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };

    private void addMessage(String username, String message) {

    }

    public void sendMessage(View view) {
        Log.i(TAG, "sendMessage: ");
        String message = binding.textField.getText().toString().trim();
        if (TextUtils.isEmpty(message)) {
            Log.i(TAG, "sendMessage:2 ");
            return;
        }
        binding.textField.setText("");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("message", message);
            jsonObject.put("username", Username);
            jsonObject.put("uniqueId", uniqueId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "sendMessage: 1" + mSocket.emit("chat message", jsonObject));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (isFinishing()) {
            Log.i(TAG, "onDestroy: ");

            JSONObject userId = new JSONObject();
            try {
                userId.put("name", Username + "sonu");
                userId.put("room_id", Username + " 123");


                mSocket.emit("connect user", userId);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            mSocket.disconnect();
            mSocket.off("chat message", onNewMessage);
            mSocket.off("connect user", onNewUser);
            mSocket.off("on typing", onTyping);
            Username = "";
            messageAdapter.clear();
        } else {
            Log.i(TAG, "onDestroy: is rotating.....");
        }

    }


}
