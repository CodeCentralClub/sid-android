package com.codecentral.sid.shared.ui.nearby.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codecentral.sid.shared.R

/**
 * An adapter that presents given endpoint IDs in a list.
 *
 * @see EndpointSelectionListener
 */
class EndpointRecyclerAdapter(private val listener: EndpointSelectionListener? = null) :
        RecyclerView.Adapter<EndpointViewHolder>() {

    private val endpoints = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EndpointViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.endpoint_viewholder, parent, false)
        return EndpointViewHolder(itemView)
    }

    override fun getItemCount(): Int = endpoints.size

    override fun onBindViewHolder(holder: EndpointViewHolder, position: Int) {
        val endpoint = endpoints[position]
        holder.setEndpointName(endpoint)
        holder.setOnClickListener(View.OnClickListener {
            listener?.onEndpointSelected(endpoint)
        })
    }

    /**
     * Sets the currently displayed endpoints.
     *
     * TODO(adapter): Use DiffUtil to efficiently update items
     */
    fun updateItems(newEndpoints: List<String>) {
        endpoints.clear()
        newEndpoints.forEach {
            endpoints.add(it)
        }
    }
}

/**
 * A listener that notifies selection events.
 *
 * @see EndpointRecyclerAdapter
 */
interface EndpointSelectionListener {

    /**
     * Called when an endpoint is selected.
     *
     * @param endpointId The ID of the selected endpoint
     */
    fun onEndpointSelected(endpointId: String)
}

/**
 * A view holder that displays an individual endpoint ID.
 */
class EndpointViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val view: View = itemView

    private val endpointName: TextView

    init {
        endpointName = view.findViewById(R.id.endpoint_name)
    }

    /**
     * Sets the endpoint ID text for this view holder.
     */
    fun setEndpointName(name: String) {
        endpointName.text = name
    }

    fun setOnClickListener(listener: View.OnClickListener) {
        view.setOnClickListener(listener)
    }
}