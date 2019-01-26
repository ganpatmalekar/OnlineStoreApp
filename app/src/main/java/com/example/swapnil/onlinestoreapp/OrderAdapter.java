package com.example.swapnil.onlinestoreapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderHolder> {

    Context context;
    List<OrderModel> listItems;

    public OrderAdapter(Context context, List<OrderModel> listItems)
    {
        this.context = context;
        this.listItems = listItems;
    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_layout, viewGroup, false);
        return new OrderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderHolder orderHolder, int i) {
        final OrderModel model = listItems.get(i);

        if (!model.getDelivery_status().equalsIgnoreCase("inprocess"))
        {
            orderHolder.cancel_order.setEnabled(false);
            orderHolder.order_id.setText("Order ID: "+model.getOrder_id());
            orderHolder.status.setText("Delivery Status: "+Float.parseFloat(model.getDelivery_status()));
            orderHolder.comments.setText("Comments: "+model.getComments());
            orderHolder.bill.setText("Total Bill: "+model.getPrice());
        }
        else {
            orderHolder.cancel_order.setEnabled(true);
        }

        orderHolder.cancel_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Button clicked", Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class OrderHolder extends RecyclerView.ViewHolder {
        TextView order_id, status, comments, bill;
        Button cancel_order;

        public OrderHolder(@NonNull View itemView) {
            super(itemView);

            order_id = (TextView)itemView.findViewById(R.id.order_id);
            status = (TextView)itemView.findViewById(R.id.status);
            comments = (TextView)itemView.findViewById(R.id.comments);
            bill = (TextView)itemView.findViewById(R.id.bill);

            cancel_order = (Button)itemView.findViewById(R.id.cancel_order);
        }
    }
}
