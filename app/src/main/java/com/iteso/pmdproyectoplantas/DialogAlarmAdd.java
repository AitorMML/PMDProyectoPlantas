package com.iteso.pmdproyectoplantas;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import com.iteso.pmdproyectoplantas.beans.Alarma;

import java.util.ArrayList;

public class DialogAlarmAdd extends DialogFragment {
    private RadioGroup grupoRadioButton;
    private RadioButton recordatorioDiario;
    private RadioButton recordatorioSemanal;
    private LinearLayout grupoDiasSemana;
    private CheckBox []diasSemana;
    private CheckBox regar;
    private CheckBox cambiarTierra;
    private CheckBox desparacitar;
    private CheckBox podar;
    private CheckBox otro;
    private EditText campoOtro;
    private TimePicker timepicker;
    private DatePicker datePicker;

    public interface NoticeDialogListener {
        void onDialogPositiveClick(DialogFragment dialog);
        void onDialogNegativeClick(DialogFragment dialog);
    }

    NoticeDialogListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (NoticeDialogListener) context;
    }

    private void bindUIelements(View v) {
        recordatorioDiario = v.findViewById(R.id.dialog_add_radiobtn_diario);
        recordatorioSemanal = v.findViewById(R.id.dialog_add_radiobtn_semanal);
        grupoDiasSemana = v.findViewById(R.id.dialog_add_grupo_dias);
        diasSemana = new CheckBox[7];
        diasSemana[0] = v.findViewById(R.id.dialog_add_lunes);
        diasSemana[1] = v.findViewById(R.id.dialog_add_martes);
        diasSemana[2] = v.findViewById(R.id.dialog_add_miercoles);
        diasSemana[3] = v.findViewById(R.id.dialog_add_jueves);
        diasSemana[4] = v.findViewById(R.id.dialog_add_viernes);
        diasSemana[5] = v.findViewById(R.id.dialog_add_sabado);
        diasSemana[6] = v.findViewById(R.id.dialog_add_domingo);
        regar = v.findViewById(R.id.dialog_add_alarm_regar);
        cambiarTierra = v.findViewById(R.id.dialog_add_alarm_cambiar_tierra);
        desparacitar = v.findViewById(R.id.dialog_add_alarm_desparacitar);
        podar = v.findViewById(R.id.dialog_add_alarm_podar);
        otro = v.findViewById(R.id.dialog_add_alarm_otro);
        campoOtro = v.findViewById(R.id.dialog_add_otro_texto);
        grupoRadioButton = v.findViewById(R.id.dialog_add_grupo_diario_semanal);
        datePicker = v.findViewById(R.id.dialog_add_datepicker);
        timepicker = v.findViewById(R.id.dialog_add_timepicker);
        recordatorioSemanal.setChecked(true);
        grupoDiasSemana.setEnabled(true);
        grupoRadioButton.setOnCheckedChangeListener((RadioGroup grupo, int elem)->{
            switch (elem) {
                case R.id.dialog_add_radiobtn_diario:
                    grupoDiasSemana.setEnabled(false);
                    datePicker.setVisibility(View.VISIBLE);
                    for(CheckBox dia : diasSemana) { dia.setEnabled(false); }
                    break;
                case R.id.dialog_add_radiobtn_semanal:
                    grupoDiasSemana.setEnabled(true);
                    datePicker.setVisibility(View.GONE);
                    for(CheckBox dia : diasSemana) { dia.setEnabled(true); }
                    break;
            }
        });
        otro.setOnCheckedChangeListener((CompoundButton chckBox, boolean seleccionado)->{
            if(seleccionado) {
                chckBox.setText(null);
                campoOtro.setVisibility(View.VISIBLE);
            } else {
                chckBox.setText(R.string.texto_comun_otro);
                campoOtro.setVisibility(View.INVISIBLE);
            }
        });

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View vista = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_alarm, null);
        bindUIelements(vista);
        builder.setView(vista)
                .setTitle(R.string.dialog_add_titulo)
                .setPositiveButton(R.string.texto_comun_aceptar, (DialogInterface dialog, int id)->{
                    mListener.onDialogPositiveClick(DialogAlarmAdd.this);
                })
                .setNegativeButton(R.string.texto_comun_cancelar, (DialogInterface dialog, int id)->{
                    mListener.onDialogNegativeClick(DialogAlarmAdd.this);
                });
        return builder.create();
    }

    @TargetApi(23)
    public Alarma getAlarmData() {
        Alarma alarma = new Alarma();
        alarma.setOnce(recordatorioDiario.isChecked());
        //alarma.setDaysOfWeek(new ArrayList<Boolean>(7));
        for(int i=0;i<diasSemana.length;++i) { alarma.getDaysOfWeek().add(i, diasSemana[i].isChecked()); }
        alarma.setRegar(regar.isChecked());
        alarma.setCambiarTierra(cambiarTierra.isChecked());
        alarma.setDesparacitar(desparacitar.isChecked());
        alarma.setPodar(podar.isChecked());
        alarma.setOtro(otro.isChecked());
        alarma.setOtroCampo(campoOtro.getText().toString());
        alarma.setTime(timepicker.getHour()+":"+timepicker.getMinute());
        alarma.setDate(datePicker.getYear()+"-"+datePicker.getMonth()+"-"+datePicker.getDayOfMonth());
        return alarma;
    }
}
