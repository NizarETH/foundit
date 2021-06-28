package com.app.foundit.activities;

 import androidx.appcompat.app.AppCompatActivity;

 import android.os.Bundle;
import android.content.Intent;

import android.os.Handler;

import com.app.foundit.R;
import com.app.foundit.beans.Actualite;
import com.app.foundit.beans.MyObject;
import com.app.foundit.beans.User;
import io.realm.Realm;

public class SplashActivity extends  AppCompatActivity {


    @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);



            Realm r = Realm.getDefaultInstance();
            r.beginTransaction();
            r.deleteAll();

            User user = r.createObject(User.class, "1");
            user.setFirst_name("Arron");
            user.setLast_name("Hunt");
            user.setEmail("user@gmail.com");
            user.setPhone("0600000000");
            user.setPicture("https://randomuser.me/img/creator_arron.png");
            user.setPassword("1234");
            user.setAdmin(false);

            User admin = r.createObject(User.class, "2");
            admin.setFirst_name("Adam");
            admin.setLast_name("Notice");
            admin.setEmail("admin@gmail.com");
            admin.setPhone("0600000001");
            admin.setPicture("https://randomuser.me/img/creator_keith.png");
            admin.setPassword("0000");
            admin.setAdmin(true);

            MyObject myObject = r.createObject(MyObject.class,"1");
            myObject.setCategory("file");
            myObject.setName("CIN");
            myObject.setPicture("https://www.cnie.ma/assets/img/theme/front.jpg");
            myObject.setFounder(user);
            myObject.setLocation("Biblio");
            myObject.setDate("24/06/2021");
            myObject.setDetails("J'ai trouvé ce CIN dans la biblio.");

            MyObject myObject1 = r.createObject(MyObject.class,"2");
            myObject1.setCategory("keys");
            myObject1.setName("Clés trouvées");
            myObject1.setPicture("https://misterminit.eu/assets/files/site/Keys-MISTERMINIT.JPG");
            myObject1.setLocation("Parking");
            myObject1.setFounder(admin);
            myObject1.setDate("26/06/2021");
            myObject1.setDetails("J'ai trouvé ces clés dans le parking de l'école.");


            Actualite actualite = r.createObject(Actualite.class, "1");
            actualite.setData("Avis de vérificatin de la situation administrative d'amo (Etudiants).");
            actualite.setDate("Le 02/03/2021.");


            Actualite actualite1 = r.createObject(Actualite.class, "2");
            actualite1.setData("Avis de travaux dirigés en présentiel du module Biliogie, (BCG-S2).");
            actualite1.setDate("Le 12/03/2021.");

            Actualite actualite2 = r.createObject(Actualite.class, "3");
            actualite2.setData("Avis de travaux dirigés en présentiel du module Informatique, (Info-S3).");
            actualite2.setDate("Le 10/03/2021.");

            Actualite actualite3 = r.createObject(Actualite.class, "4");
            actualite3.setData("Avis de travaux pratiques à distance du module Mathématique, (Indus-S5).");
            actualite3.setDate("Le 10/03/2021.");

            r.commitTransaction();
            /** Sets a layout for this activity */

            Handler h = new Handler();
            h.postDelayed(() -> {
                Intent intent =new Intent(getApplicationContext(), MainActivity.class);
                 startActivity(intent);
                 finish();
            }, 1000);

        }
    }