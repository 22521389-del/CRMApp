package com.example.crmmobile;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crmmobile.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ListView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements main_screen.onModuleItemSelectedListener{
    private ViewPager2 viewPager2;
    private BottomNavigationView navFooter;
    FrameLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager2 = findViewById(R.id.viewPager);
        navFooter = findViewById(R.id.nav_footer);
        container = findViewById(R.id.main_container);

        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(adapter);

        navFooter.setOnItemSelectedListener(item->{
            int itemID = item.getItemId();

            //return home
            if(container.getVisibility() == View.VISIBLE){
                getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                container.setVisibility(View.GONE);
                viewPager2.setVisibility(View.VISIBLE);
            }

            if(item.getItemId() == R.id.nav_home){
                viewPager2.setCurrentItem(0);
                return true;
            } else if (item.getItemId() == R.id.nav_lead) {
                viewPager2.setCurrentItem(1);
                return true;
            }
            return false;
        });

        //back to main screen
        getSupportFragmentManager().addOnBackStackChangedListener(()->{
            Fragment current = getSupportFragmentManager().findFragmentById(R.id.main_container);
            if(current == null){
                viewPager2.setVisibility(View.VISIBLE);
                container.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onModuleSelectedListener(String moduleName){
        if(moduleName.equals("Báo giá")){
            Fragment quoteFragment = new QuoteFragment();


            getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, quoteFragment)
                .addToBackStack(null)
                .commit();
            findViewById(R.id.viewPager).setVisibility(View.GONE);
            findViewById(R.id.main_container).setVisibility(View.VISIBLE);

            BottomNavigationView navigationView = findViewById(R.id.nav_footer);
            navigationView.getMenu().findItem(R.id.nav_menu).setChecked(true);
            }
    }

}