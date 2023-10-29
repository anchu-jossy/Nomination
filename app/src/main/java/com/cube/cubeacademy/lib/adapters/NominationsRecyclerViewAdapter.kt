package com.cube.cubeacademy.lib.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cube.cubeacademy.R
import com.cube.cubeacademy.databinding.ViewNominationListItemBinding
import com.cube.cubeacademy.lib.models.Nomination
import com.cube.cubeacademy.lib.models.Nominee

/**
 * Adapter for displaying a list of nominations in a RecyclerView.
 *
 * @param nominations The list of nominations, each paired with a nominee to show name instead of id.
 */
class NominationsRecyclerViewAdapter(private val nominations: List<Pair<Nomination, Nominee>>) :
	ListAdapter<Nomination, NominationsRecyclerViewAdapter.ViewHolder>(DIFF_CALLBACK) {

	// ViewHolder to hold the views
	class ViewHolder(val binding: ViewNominationListItemBinding) : RecyclerView.ViewHolder(binding.root)

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		// Inflate the layout for a single item in the RecyclerView
		val binding = ViewNominationListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return ViewHolder(binding)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val (nomination, nominee) = nominations[position]
		holder.binding.apply {
			// Set the nominee's name using string resources with placeholders
			val nameString = holder.itemView.context.getString(
				R.string.nominee_name, nominee.firstName, nominee.lastName
			)
			name.text = nameString
			reason.text = nomination.reason
		}
	}

	override fun getItemCount() = nominations.size

	companion object {
		// Callback to calculate the difference between old and new items
		val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Nomination>() {
			override fun areItemsTheSame(oldItem: Nomination, newItem: Nomination) =
				oldItem.nominationId == newItem.nominationId

			override fun areContentsTheSame(oldItem: Nomination, newItem: Nomination) =
				oldItem == newItem
		}
	}
}
