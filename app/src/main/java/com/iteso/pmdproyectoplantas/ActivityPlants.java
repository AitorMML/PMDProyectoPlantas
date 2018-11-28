package com.iteso.pmdproyectoplantas;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import com.iteso.pmdproyectoplantas.adapters.AdapterGrupo;
import com.iteso.pmdproyectoplantas.tools.Constants;

public class ActivityPlants extends NavigationDrawerImp {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    SectionsPagerAdapter mSectionsPagerAdapter;
    private FragmentPlantas fragmentPlantas;
    private FragmentPlantsGrupos fragmentPlantsGrupos;

    private SearchView mSearchView;
    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plants);

        getSupportActionBar().setTitle(R.string.drawer_my_plants);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.activity_plants_viewpager);
        viewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout = findViewById(R.id.activity_plants_tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setSearchViewHint(tab.getText().toString().toLowerCase());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                setSearchViewHint(tab.getText().toString().toLowerCase());
            }
        });
        fab = (FloatingActionButton) findViewById(R.id.add_plant);
        fab.setOnClickListener((View v)->{
            if(tabLayout.getSelectedTabPosition() == 0) {
                //Actividad para agregar plantas
                Intent intent = new Intent(this, ActivityAddPlant.class);
                startActivityForResult(intent, Constants.addPlantIntentId);
            } else if(tabLayout.getSelectedTabPosition() == 1) {
                Snackbar.make(v, "Agregar grupo de plantas", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        tabLayout.clearOnTabSelectedListeners();
        super.onDestroy();
    }

    public void setSearchViewHint(String tabText) {
        if(mSearchView != null) {
            mSearchView.setQueryHint(getString(R.string.activity_plants_search_hint)
                    +" en "+tabText+"...");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constants.addPlantIntentId) {
            fragmentPlantas.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean val = super.onCreateOptionsMenu(menu);
        MenuItem mSearch = menu.findItem(R.id.activity_plants_buscar_btn);
        if(mSearch != null && mSearch.getActionView().getClass() == SearchView.class) {
            mSearchView = (SearchView) mSearch.getActionView();
            setSearchViewHint(tabLayout.getTabAt(tabLayout.getSelectedTabPosition())
                    .getText().toString().toLowerCase());
            mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    ActivityPlants.this.onQueryTextChange(newText);
                    return true;
                }
            });
            //TODO: Regresar la lista de los Adapters a la normalidad cuando ya no se esté buscando
            /*mSearchView.setOnCloseListener(null);
            mSearchView.setOnQueryTextFocusChangeListener(null);*/
        } else val = false;

        return val;
    }

    private void onQueryTextChange(String text) {
        ((Filterable)getSupportFragmentManager().getFragments().get(tabLayout.getSelectedTabPosition())).getFilter().filter(text);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public  SectionsPagerAdapter(FragmentManager fm) {super(fm);}

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    if(fragmentPlantas == null) {
                        fragmentPlantas = new FragmentPlantas();
                    }
                    return fragmentPlantas;
                case 1:
                    if(fragmentPlantsGrupos == null) {
                        fragmentPlantsGrupos = new FragmentPlantsGrupos();
                    }
                    return fragmentPlantsGrupos;
                default: return fragmentPlantas != null ? fragmentPlantas : (fragmentPlantas = new FragmentPlantas());
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0: return getString(R.string.activity_plants_tab_plantas);
                case 1: return getString(R.string.activity_plants_tab_grupos);
            }

            return null;
        }
    }
}
