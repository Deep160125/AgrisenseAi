package com.ninjaTurtles.agrisense.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ninjaTurtles.agrisense.R;
import com.ninjaTurtles.agrisense.activities.IrrigationActivity;
import com.ninjaTurtles.agrisense.activities.RecommendationsActivity;
import com.ninjaTurtles.agrisense.activities.SensorDataActivity;
import com.ninjaTurtles.agrisense.adapters.RecommendationAdapter;
import com.ninjaTurtles.agrisense.models.IrrigationStatus;
import com.ninjaTurtles.agrisense.models.Recommendation;
import com.ninjaTurtles.agrisense.models.SensorData;
import com.ninjaTurtles.agrisense.models.WeatherInfo;
import com.ninjaTurtles.agrisense.utils.AnimationHelper;
import com.ninjaTurtles.agrisense.viewmodels.HomeViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel viewModel;

    private View headerHome, viewLiveDot;
    private MaterialCardView cardMoisture, cardTemp, cardHumidity, cardTank, cardIrrigationControl, cardWeather;
    private TextView tvValueMoisture, tvValueTemp, tvValueHumidity, tvValueTank, tvHomePumpStatus;
    private TextView tvWeatherCondition, tvWeatherLocation, tvWeatherTemp, tvViewAllRecommendations;
    private MaterialButton btnQuickControlIrrigation;
    private RecyclerView rvHomeRecommendations;

    private RecommendationAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        headerHome = v.findViewById(R.id.headerHome);
        viewLiveDot = v.findViewById(R.id.viewLiveDot);

        cardMoisture = v.findViewById(R.id.cardMoisture);
        cardTemp = v.findViewById(R.id.cardTemp);
        cardHumidity = v.findViewById(R.id.cardHumidity);
        cardTank = v.findViewById(R.id.cardTank);
        cardIrrigationControl = v.findViewById(R.id.cardIrrigationControl);
        cardWeather = v.findViewById(R.id.cardWeather);

        tvValueMoisture = v.findViewById(R.id.tvValueMoisture);
        tvValueTemp = v.findViewById(R.id.tvValueTemp);
        tvValueHumidity = v.findViewById(R.id.tvValueHumidity);
        tvValueTank = v.findViewById(R.id.tvValueTank);
        tvHomePumpStatus = v.findViewById(R.id.tvHomePumpStatus);

        tvWeatherCondition = v.findViewById(R.id.tvWeatherCondition);
        tvWeatherLocation = v.findViewById(R.id.tvWeatherLocation);
        tvWeatherTemp = v.findViewById(R.id.tvWeatherTemp);
        tvViewAllRecommendations = v.findViewById(R.id.tvViewAllRecommendations);

        btnQuickControlIrrigation = v.findViewById(R.id.btnQuickControlIrrigation);
        rvHomeRecommendations = v.findViewById(R.id.rvHomeRecommendations);

        rvHomeRecommendations.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RecommendationAdapter();
        rvHomeRecommendations.setAdapter(adapter);

        setupClickListeners();

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        runStaggeredDashboardAnimations();
        AnimationHelper.startPulse(getContext(), viewLiveDot);

        observeViewModel();
    }

    private void observeViewModel() {
        viewModel.getSensorData().observe(getViewLifecycleOwner(), new Observer<SensorData>() {
            @Override
            public void onChanged(SensorData data) {
                if (data != null) {
                    AnimationHelper.animateNumberValue(tvValueMoisture, 0, data.getSoilMoisture(), "%", 800);
                    AnimationHelper.animateNumberValue(tvValueHumidity, 0, data.getHumidity(), "%", 800);
                    AnimationHelper.animateNumberValue(tvValueTank, 0, data.getWaterTankLevel(), "%", 800);
                    tvValueTemp.setText(data.getTemperature() + "°C");
                }
            }
        });

        viewModel.getWeatherInfo().observe(getViewLifecycleOwner(), new Observer<WeatherInfo>() {
            @Override
            public void onChanged(WeatherInfo weather) {
                if (weather != null) {
                    tvWeatherCondition.setText(weather.getCondition());
                    tvWeatherLocation.setText(weather.getLocation() + " • " + weather.getRainProbability() + "% Rain Prob");
                    tvWeatherTemp.setText(weather.getTemperature() + "°C");
                }
            }
        });

        viewModel.getRecommendations().observe(getViewLifecycleOwner(), new Observer<List<Recommendation>>() {
            @Override
            public void onChanged(List<Recommendation> recommendations) {
                adapter.setRecommendations(recommendations);
            }
        });

        viewModel.getIrrigationStatus().observe(getViewLifecycleOwner(), new Observer<IrrigationStatus>() {
            @Override
            public void onChanged(IrrigationStatus status) {
                if (status != null) {
                    if (status.getPumpState() == IrrigationStatus.PumpState.ON) {
                        tvHomePumpStatus.setText(getString(R.string.pump_status_on));
                    } else if (status.getPumpState() == IrrigationStatus.PumpState.STARTING) {
                        tvHomePumpStatus.setText(getString(R.string.pump_status_starting));
                    } else {
                        tvHomePumpStatus.setText(getString(R.string.pump_status_off));
                    }
                }
            }
        });
    }

    private void runStaggeredDashboardAnimations() {
        if (getContext() == null) return;

        // Header fade in
        Animation fadeIn = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        headerHome.startAnimation(fadeIn);

        // Sensor Cards staggered entry
        Animation anim1 = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up);
        anim1.setStartOffset(100);
        cardMoisture.startAnimation(anim1);

        Animation anim2 = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up);
        anim2.setStartOffset(180);
        cardTemp.startAnimation(anim2);

        Animation anim3 = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up);
        anim3.setStartOffset(260);
        cardHumidity.startAnimation(anim3);

        Animation anim4 = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up);
        anim4.setStartOffset(340);
        cardTank.startAnimation(anim4);

        // Irrigation Card scale/fade
        Animation scaleAnim = AnimationUtils.loadAnimation(getContext(), R.anim.scale_up);
        scaleAnim.setStartOffset(400);
        cardIrrigationControl.startAnimation(scaleAnim);

        // Weather card fade
        Animation fadeWeather = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        fadeWeather.setStartOffset(480);
        cardWeather.startAnimation(fadeWeather);
    }

    private void setupClickListeners() {
        btnQuickControlIrrigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationHelper.animateButtonPress(getContext(), btnQuickControlIrrigation);
                startActivity(new Intent(getActivity(), IrrigationActivity.class));
            }
        });

        cardMoisture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SensorDataActivity.class));
            }
        });

        tvViewAllRecommendations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), RecommendationsActivity.class));
            }
        });
    }
}
