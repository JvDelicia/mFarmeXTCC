package com.example.a3aetim.Myndie;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.a3aetim.Myndie.Classes.ImageDAO;
import com.example.a3aetim.Myndie.helper.DatabaseHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import es.dmoral.toasty.Toasty;

import static android.media.MediaRecorder.VideoSource.CAMERA;
import static android.widget.Toast.LENGTH_SHORT;

public class CadastroCli extends Activity implements AdapterView.OnItemSelectedListener {

    DatabaseHelper helper;
    EditText txtName, txtEmail,txtUser, txtPass, txtBirth;
    Spinner country,lang;
    String crtdata;
    int countryid,langid,PICK_IMAGE = 100;
    Button btnChoosePic;
    SimpleDateFormat df;
    Uri imageURI;
    ImageView imgvArchive;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cli);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        btnChoosePic = (Button)findViewById(R.id.btnChoosePic);

        df = new SimpleDateFormat("dd/MM/yyyy");

        helper = new DatabaseHelper(this);

        country = (Spinner)findViewById(R.id.spnCountry);
        lang = (Spinner)findViewById(R.id.spnLang);
        country.setOnItemSelectedListener(this);
        lang.setOnItemSelectedListener(this);
        btnChoosePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });

        txtName = (EditText)findViewById(R.id.edtName);
        txtUser = (EditText)findViewById(R.id.edtUser);
        txtPass = (EditText)findViewById(R.id.edtPass);
        txtEmail = (EditText)findViewById(R.id.edtEmail);
        //TODO: Criar um DatePickerDialog
        txtBirth = (EditText)findViewById(R.id.edtBirth);

        crtdata = sdf.format(new Date());

        imgvArchive = (ImageView)findViewById(R.id.imgvArchive);

        List<String> paises = new ArrayList<>(Arrays.asList("Brasil","Estados Unidos","Canadá"));
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, paises );
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        country.setAdapter(dataAdapter);

        List<String> linguagens = new ArrayList<>(Arrays.asList("Português","Inglês"));
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, linguagens );
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lang.setAdapter(dataAdapter2);


    }

    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        pictureDialog.setTitle("Selecione");
        String[] pictureDialogItems = {
                "Selecione uma foto da galeria",
                "Tire uma foto pela câmera" };
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }
    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, PICK_IMAGE);
    }

    private void takePhotoFromCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 3);
        }
        else{
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);}
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == PICK_IMAGE) {
            if (data != null) {
                imageURI = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageURI);
                    imgvArchive.setImageBitmap(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toasty.error(this,"Too bad", Toast.LENGTH_SHORT,false).show();
                }
            }

        } else if (requestCode == CAMERA) {
            bitmap = (Bitmap) data.getExtras().get("data");
            imgvArchive.setImageBitmap(bitmap);
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public void Cad(View view){
        tryCad();
    }

    public void cadUser(){
        String img = ImageDAO.saveToInternalStorage(bitmap,this);;
        //byte[] img = getBitmapAsByteArray(bitmap);
        countryid = country.getSelectedItemPosition();
        langid = lang.getSelectedItemPosition();
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values =  new ContentValues();
        values.put("LoginUser", String.valueOf(txtUser.getText()));
        values.put("PassUser",String.valueOf(txtPass.getText()));
        values.put("NameUser",String.valueOf(txtName.getText()));
        values.put("BirthUser",String.valueOf(txtBirth.getText()));
        values.put("EmailUser",String.valueOf(txtEmail.getText()));
        values.put("PicUser",img);
        values.put("CountryUser",countryid);
        values.put("TypeUser",0);
        values.put("CrtDateUser",crtdata);
        values.put("IdLang",langid);
        long res = db.insert("User",null,values);
        if(res != -1){
            Toasty.success(this, "Cadastrado com sucesso!", LENGTH_SHORT,false).show();
        }
        else {
            Toasty.error(this, "Um erro ocorreu!", LENGTH_SHORT,false).show();
        }
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    @Override
    protected void onDestroy(){
        helper.close();
        super.onDestroy();
    }

    public void CadProd(View view){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values =  new ContentValues();

        values.put("InfoDev","Criador de conteudo");
        values.put("NumSoftDev","1");
        values.put("NameDev","Vortex");

        long res = db.insert("Developer",null,values);

        ContentValues values2 =  new ContentValues();
        for(int i = 0; i<10; i++) {
            values2.put("NameApp", "Yhe escape"+i);
            values2.put("VersionApp", "Pré-Alpha 0.0.1");
            values2.put("PriceApp", 133.30 + i);
            values2.put("PublisherNameApp", "BG");
            values2.put("ReleaseDateApp", "08/02/2018");
            values2.put("ArquiveApp", "arquivo.rar");
            values2.put("DescApp","Descrição teste aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            values2.put("IdDev", 1);
            values2.put("TypeApp", 1);
            values2.put("PegiApp", 2);
            db.insert("Application",null,values2);
        }
        if(res != -1){
            Toasty.success(this, "Cadastrado com sucesso!", LENGTH_SHORT,false).show();
            CadGenre();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        else {
            Toasty.error(this, "Um erro ocorreu!", LENGTH_SHORT,false).show();
        }
    }

    public void CadGenre(){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values =  new ContentValues();

        values.put("NameGen","Ação");
        db.insert("Genre",null,values);
        values.put("NameGen","Avenura");
        db.insert("Genre",null,values);
        values.put("NameGen","Estratégia");
        db.insert("Genre",null,values);
        values.put("NameGen","RPG");
        db.insert("Genre",null,values);
        values.put("NameGen","Esporte");
        db.insert("Genre",null,values);
        values.put("NameGen","Corrida");
        db.insert("Genre",null,values);
        values.put("NameGen","Simulação");
        db.insert("Genre",null,values);
        values.put("NameGen","RTS");
        db.insert("Genre",null,values);
        values.put("NameGen","Romance");
        db.insert("Genre",null,values);
        values.put("NameGen","FPS");
        long res = db.insert("Genre",null,values);

        ContentValues values2 =  new ContentValues();
        for(int i = 0; i<10; i++) {
            values2.put("_IdApp_FK",i+1 );
            values2.put("_IdGen_FK", i+1);
            db.insert("ApplicationGenre",null,values2);
        }
        if(res != -1){
            Toasty.success(this, "Cadastrado com sucesso!", LENGTH_SHORT,false).show();
        }
        else {
            Toasty.error(this, "Um erro ocorreu!", LENGTH_SHORT,false).show();
        }
    }

    public void tryCad(){
        String name =txtName.getText().toString();
        String password = txtPass.getText().toString();
        String email = txtEmail.getText().toString();
        String user = txtUser.getText().toString();
        String birth = txtBirth.getText().toString();
        Date d = new Date();

        txtEmail.setError(null);
        //txtBirth.setError(null);
        //TODO: Mudar isso (Deixa que eu mudo Igor)
        txtName.setError(null);
        txtPass.setError(null);
        txtUser.setError(null);

        boolean cancel = false;
        View focusView = null;

        if(TextUtils.isEmpty(birth)){
            txtBirth.setError(getString(R.string.error_field_required));
            cancel = true;
            focusView = txtBirth;
        }
        else if(!isDateValid()){
            txtBirth.setError(getString(R.string.error_invalid_date));
            cancel = true;
            focusView = txtBirth;
        }

        if(TextUtils.isEmpty(password)){
            txtPass.setError(getString(R.string.error_field_required));
            cancel = true;
            focusView = txtPass;
        }
        else if(!isPasswordValid(password)){
            txtPass.setError(getString(R.string.error_invalid_password));
            cancel = true;
            focusView = txtPass;
        }

        if(TextUtils.isEmpty(user)){
            txtUser.setError(getString(R.string.error_field_required));
            cancel = true;
            focusView = txtUser;
        }
        else if(!isUserNameValid(user)){
            txtUser.setError(getString(R.string.error_invalid_user));
            cancel = true;
            focusView = txtUser;
        }

        if(TextUtils.isEmpty(email)){
            txtEmail.setError(getString(R.string.error_field_required));
            cancel = true;
            focusView = txtEmail;
        }
        else if(!isEmailValid(email)){
            txtEmail.setError(getString(R.string.error_invalid_email));
            cancel = true;
            focusView = txtEmail;
        }

        if(TextUtils.isEmpty(name)){
            txtName.setError(getString(R.string.error_field_required));
            cancel = true;
            focusView = txtName;
        }
        else if(!isNameValid(name)){
            txtName.setError(getString(R.string.error_invalid_name));
            cancel = true;
            focusView = txtName;
        }

        if(cancel){
            //Código caso algum campo esteja inválido
            focusView.requestFocus();
        }
        else{
            cadUser();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }



    }

    public boolean isNameValid(String name){
        return name.length() > 0;
    }

    public boolean isEmailValid(String email){
        return email.contains("@") && email.contains(".");
    }

    public boolean isUserNameValid(String user){
        //Lógica para validar nome de usuário
        return true;
    }

    public boolean isPasswordValid(String password){
        return password.length() > 4;
    }

    public boolean isDateValid(){
        return true;
    }

    public boolean isPicUserValid(){
        //Lógica para validar foto
        return true;
    }




}
