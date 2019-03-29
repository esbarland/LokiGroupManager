### Les preuves prouvant les compétences demandées 
***
##### Je sais utiliser les Intent pour faire communiquer deux activités :
```
public void clickeventSettings(MenuItem item) {
    Intent intent = new Intent(this, SettingsActivity.class);
    startActivity(intent);
}
```

##### Je sais développer en utilisant le SDK le plus bas possible :

<uses-sdk
android:minSdkVersion = 15
android:targetSdkVersion = 28 />

##### Je sais distinguer mes ressources en utilisant les qualifier :

##### Je sais modifier le manifeste de l’application en fonction de mes besoins :
```
<activity
    android:name=".Activities.SettingsActivity"
    android:label="@string/title_activity_settings"
    android:parentActivityName=".MainActivity">
    <meta-data
        android:name="android.support.PARENT_ACTIVITY"
        android:value=".MainActivity" />
</activity>
```
##### Je sais faire des vues xml en utilisant layouts et composants adéquats :
```
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <TextView
        android:id="@+id/debugMessage"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@string/settings_title"
        android:textSize="@dimen/default_font_size"/>

</LinearLayout>
```
##### Je sais coder proprement mes activités, en m’assurant qu’elles ne font que relayer les évènements :
##### Je sais coder une application en ayant un véritable métier :
##### Je sais parfaitement séparer vue et modèle :
##### Je maîtrise le cycle de vie de mon application :
##### Je maîtrise le cycle de vie de mon application :
##### Je sais gérer les permissions dynamiques de mon application :
##### Je sais gérer la persistance légère de mon application :
##### Je sais gérer la persistance profonde de mon application :
##### Je sais afficher une collection de données :
##### Je sais coder mon propre adaptateur :
##### Je maîtrise l’usage des fragments :
##### Je maîtrise l’utilisation de Git :
##### Je sais utiliser le gyroscope :

