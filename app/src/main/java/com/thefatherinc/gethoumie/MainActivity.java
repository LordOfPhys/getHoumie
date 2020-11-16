package com.thefatherinc.gethoumie;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.json.JSONException;
import org.json.JSONObject;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


        JSONObject jsonObjectParams =new JSONObject();
        try {
            jsonObjectParams.put("password", "password123");
            jsonObjectParams.put("name", "Test");
            jsonObjectParams.put("birthday", "10.10.1995");
            jsonObjectParams.put("gender", "1");
            jsonObjectParams.put("usr_lang", "ru");
            jsonObjectParams.put("dis_lang", "русский");
            jsonObjectParams.put("dev_id", "3d652443de43e9c2");
            jsonObjectParams.put("dev_mod", "SM-A320F");
            jsonObjectParams.put("func", "create_acc");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        f.channel().writeAndFlush(Unpooled.copiedBuffer(jsonObjectParams.toString(), CharsetUtil.UTF_8);
    }

}