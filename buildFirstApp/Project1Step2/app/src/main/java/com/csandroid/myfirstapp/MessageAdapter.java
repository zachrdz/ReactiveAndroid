package com.csandroid.myfirstapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.csandroid.myfirstapp.models.Message;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Message> messageList;

    public MessageAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }


    @Override
    public int getItemCount() {
        return messageList.size();
    }

    @Override
    public void onBindViewHolder(MessageViewHolder messageViewHolder, int i) {
        final Message ci = messageList.get(i);

        final MessageViewHolder mvh = messageViewHolder;

        messageViewHolder.vSender.setText(ci.getSenderUsername());
        messageViewHolder.vSubject.setText(ci.getSubject());
        messageViewHolder.vTTL.setText(String.valueOf(ci.getTTL()));

        final int ttlInMillis = ci.getTTL() * 1000;

        new CountDownTimer(ttlInMillis, 1000) {

            public void onTick(long millisUntilFinished) {
                mvh.vTTL.setText("TTL: " + millisUntilFinished / 1000 + " sec");
                mvh.vTTLBar.setProgress((ttlInMillis * 1000));
            }

            public void onFinish() {
                mvh.vTTL.setText("DELETED");
                mvh.vSender.setBackgroundResource(R.color.softred);
            }
        }.start();

        messageViewHolder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Context context = v.getContext();
                Intent intent = new Intent(context, ReadMessageActivity.class);

                //Create the bundle
                Bundle bundle = new Bundle();

                //Add your data to bundle
                bundle.putString("message_id", Integer.toString(ci.getId()));

                //Add the bundle to the intent
                intent.putExtras(bundle);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.cards_main, viewGroup, false);

        return new MessageViewHolder(itemView);
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {

        protected TextView vSender;
        protected TextView vSubject;
        protected TextView vTTL;
        protected ProgressBar vTTLBar;

        public MessageViewHolder(View v) {
            super(v);

            vSender = (TextView) v.findViewById(R.id.sender);
            vSubject = (TextView) v.findViewById(R.id.subject);
            vTTL = (TextView) v.findViewById(R.id.ttl);
            vTTLBar = (ProgressBar) v.findViewById(R.id.ttlBar);
        }

    }

}
