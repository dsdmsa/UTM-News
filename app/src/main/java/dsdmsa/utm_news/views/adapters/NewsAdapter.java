package dsdmsa.utm_news.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import dsdmsa.utm_news.models.News;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {


    private List<News> newsList;

    public void setNewses(List<News> orderDTOs) {
        newsList.clear();
        newsList.addAll(orderDTOs);
        notifyDataSetChanged();
    }

    public void addNewses(List<News> orderDTOs) {
        newsList.addAll(orderDTOs);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
//
//    @Override
//    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.muume_item_orders_list,
//                parent, false));
//    }
//
//    @Override
//    public void onBindViewHolder(final NewsAdapter.ViewHolder holder, int position) {
//        final OrderDTO order = orders.get(position);
//
//        long now = System.currentTimeMillis();
//        CharSequence relativeTime = DateUtils.getRelativeTimeSpanString(order.getCreationTime(), now, DateUtils
//                .MINUTE_IN_MILLIS);
//        holder.productImage.setImageFromURL(QRCodeUtils.getQRCode(order.getQrCodeHash()), screenSize.x);
//        holder.rootView.setOnClickListener(v -> {
//            if (order.isExpanded()) {
//                collapseOrder(holder);
//                order.setExpanded(false);
//            } else {
//                expandOrder(holder, order);
//                order.setExpanded(true);
//            }
//        });
//
//        holder.orderDetails.setText(
//                appearance.roundedCorners.withText(relativeTime.toString() + "\n"),
//                appearance.secondary.withText(context.getString(R.string.please_show_this_qr))
//        );
//
//        Currency currency = Currency.values()[order.getOrderDatesForAdapter().get(CURRENCY)];
//        int price = order.getOrderDatesForAdapter().get(PRICE);
//        int quantity = order.getOrderDatesForAdapter().get(QUANTITY);
//
//        holder.orderTotal.setText(
//                appearance.accent.withText(currency + " " + MoneyUtils.centsToString(price)),
//                appearance.secondary.withText(context.getString(R.string.payment_for_item)),
//                appearance.accent.withText(quantity + " " + context.getResources().getQuantityString(R.plurals
//                        .numberPieces, quantity, quantity))
//        );
//
//        if (order.isExpanded()) {
//            expandOrder(holder, order);
//        } else {
//            collapseOrder(holder);
//        }
//    }
//
//
//    private void expandOrder(ViewHolder holder, OrderDTO order) {
//        holder.expandedLine.setVisibility(View.VISIBLE);
//        holder.expandableView.setOrientation(LinearLayout.VERTICAL);
//        holder.productImage.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.MATCH_PARENT));
//        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        holder.insertPoint.removeAllViews();
//        for (OrderedProductDTO orderedProductDTO : order.getOrderedProductDTOs()) {
//            View view = layoutInflater.inflate(R.layout.muume_item_row_product, null);
//            PrettyText prettyText = (PrettyText) view.findViewById(R.id.product_name);
//
//            SDKImageView productImage = (SDKImageView) view.findViewById(R.id.user_picture_background);
//
//            productImage.setImageFromURL(sandbox.getMuumeEndpoint() + MUUME_IMAGE_STORAGE + orderedProductDTO
//                    .getProductImage());
//
//            // TODO: 12/28/16 use stirng.format method
//
//            prettyText.setText(
//                    appearance.white.withText(orderedProductDTO.getTitle() + "\n"),
//                    appearance.accent.withText(orderedProductDTO.getCurrency().name() + " " + MoneyUtils
//                            .centsToString(orderedProductDTO.getAmount()) + " "),
//                    appearance.secondary.withText(context.getString(R.string.payment_for_item)),
//                    appearance.accent.withText(orderedProductDTO.getProductType() == ProductType.piece ?
//                            orderedProductDTO.getPurchasedQuantity().intValue() + " " + context.getResources()
//                                    .getQuantityString(R.plurals.numberPieces, orderedProductDTO.getPurchasedQuantity
//                                            ().intValue(), orderedProductDTO.getPurchasedQuantity().intValue()) :
//                            orderedProductDTO.getPurchasedQuantity() + " " + orderedProductDTO.getProductType().name()
//                    )
//            );
//
//            holder.insertPoint.addView(view, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT));
//        }
//    }
//
//    private void collapseOrder(ViewHolder holder) {
//        holder.expandedLine.setVisibility(View.GONE);
//        holder.expandableView.setOrientation(LinearLayout.HORIZONTAL);
//        holder.productImage.setLayoutParams(new LinearLayout.LayoutParams(qrCodeSize, qrCodeSize));
//        holder.insertPoint.removeAllViews();
//    }
//
//    @Override
//    public int getItemCount() {
//        return orders.size();
//    }
//
//    class ViewHolder extends RecyclerView.ViewHolder {
//        private SDKImageView productImage;
//        private PrettyText orderDetails;
//        private PrettyText orderTotal;
//        private View rootView;
//        private SDKLinearLayoutDark expandableView;
//        private View expandedLine;
//        private SDKLinearLayoutDark insertPoint;
//
//        ViewHolder(View itemView) {
//            super(itemView);
//            rootView = itemView;
//            productImage = (SDKImageView) itemView.findViewById(R.id.user_picture_background);
//            orderDetails = (PrettyText) itemView.findViewById(R.id.order_creation);
//            orderTotal = (PrettyText) itemView.findViewById(R.id.total);
//            expandableView = (SDKLinearLayoutDark) itemView.findViewById(R.id.expandable_view);
//            expandedLine = itemView.findViewById(R.id.expanded_line);
//            insertPoint = (SDKLinearLayoutDark) itemView.findViewById(R.id.products_items_container);
//        }
//    }
}
