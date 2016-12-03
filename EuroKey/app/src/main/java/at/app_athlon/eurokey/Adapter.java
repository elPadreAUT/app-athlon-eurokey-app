package at.app_athlon.eurokey;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by bernhardkober on 02.12.16.
 */

public class Adapter extends ArrayAdapter<RowClass> {

    Context myContext;
    int myLayoutResourceID;
    RowClass myData[] = null;

    public Adapter(Context context,int layoutResourceID, RowClass[] data){

        super(context,layoutResourceID,data);
        this.myContext = context;
        this.myLayoutResourceID = layoutResourceID;
        this.myData = data;

    }

    public View getView(int position, View convertView, ViewGroup parent){
        View row = convertView;
        RowClassHolder holder = null;

        if(row == null) {
            LayoutInflater inflater = ((Activity)myContext).getLayoutInflater();
            row = inflater.inflate(myLayoutResourceID,parent,false);
            holder = new RowClassHolder();
            holder.imagen = (ImageView) row.findViewById(R.id.image);
            holder.textView = (TextView) row.findViewById(R.id.tv);
            holder.textView2 = (TextView) row.findViewById(R.id.tv1);
            row.setTag(holder);
        }else{
            holder = (RowClassHolder)row.getTag();
        }

        RowClass RowClass = myData[position];
        holder.textView.setText(RowClass.title);
        holder.imagen.setImageResource(RowClass.icon);
        holder.textView2.setText(RowClass.subTitel);
        return row;

    }

    static class RowClassHolder{
        ImageView imagen;
        TextView textView;
        TextView textView2;

    }
}
