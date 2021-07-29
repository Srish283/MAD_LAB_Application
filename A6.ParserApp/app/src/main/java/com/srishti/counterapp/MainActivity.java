package com.srishti.counterapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {

    TextView txtv1,txtv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtv1=(TextView)findViewById(R.id.txtv1);
        txtv2=(TextView)findViewById(R.id.txtv2);

    }
    public void parseXML(View view){
        try {
            InputStream is1=getAssets().open("Weather.xml");
            DocumentBuilderFactory documentBuilderFactory=DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder=documentBuilderFactory.newDocumentBuilder();
            Document document=documentBuilder.parse(is1);

            NodeList nodeList=document.getElementsByTagName("weather");
            txtv1.setText("XML DATA");
            txtv1.setText(txtv1.getText()+"\n___________");
            for(int i=0;i<nodeList.getLength();i++)
            {
                Node node=nodeList.item(i);

                if (node.getNodeType()==Node.ELEMENT_NODE){
                    Element element=(Element)node;
                    txtv1.setText(txtv1.getText()+"\nCity:"+getValue("city",element));
                    txtv1.setText(txtv1.getText()+"\nLatitude:"+getValue("latitude",element));
                    txtv1.setText(txtv1.getText()+"\nLongitude:"+getValue("longitude",element));
                    txtv1.setText(txtv1.getText()+"\nHumidity:"+getValue("humidity",element));
                    txtv1.setText(txtv1.getText()+"\nTemperature:"+getValue("temperature",element));
                    txtv1.setText(txtv1.getText()+"\n___________");
                }
                is1.close();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    private String getValue(String tag,Element element){
        NodeList nodeList=element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node1=nodeList.item(0);
        return node1.getNodeValue();

    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void parseJSON(View view){
        StringBuilder stringBuilder=new StringBuilder();
        String json;
        try {
            InputStream is2=getAssets().open("xml.json");
            int size=is2.available();
            byte buffer[]=new  byte[size];
            is2.read(buffer);
            json=new String(buffer, StandardCharsets.UTF_8);
            JSONArray jsonArray=new JSONArray(json);
            stringBuilder.append("JSON DATA");
            stringBuilder.append("\n___________");
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                stringBuilder.append("\nCity:").append(jsonObject.getString("city")).append("\n");
                stringBuilder.append("Latitude:").append(jsonObject.getString("latitude")).append("\n");
                stringBuilder.append("Longitude:").append(jsonObject.getString("longitude")).append("\n");
                stringBuilder.append("Humidity:").append(jsonObject.getString("humidity")).append("\n");
                stringBuilder.append("Temperature:").append(jsonObject.getString("temperature")).append("\n");
                stringBuilder.append("___________");
            }
            txtv2.setText(stringBuilder.toString());
            is2.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}