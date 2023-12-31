package com.justingruenberg.beatyourneat.Dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.ImageView;

public class ImageDialog {

    Context context;

    public ImageDialog(Context context){
        this.context = context;
    }

    public void show(int drawableResource){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);


        ImageView imageView = new ImageView(context);
        imageView.setImageResource(drawableResource);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        builder.setView(imageView);

        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();

    }
}
