package id.ac.unhas.crud

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterIni : RecyclerView.Adapter<AdapterIni.IniViewHolder>() {

    private var iniList: ArrayList<ModelIni> = ArrayList()
    private var onClickItem: ((ModelIni) -> Unit)? = null
    private var onClickDeleteItem: ((ModelIni) -> Unit)? = null

    fun addItem(item: ArrayList<ModelIni>) {
        this.iniList = item
        notifyDataSetChanged()
    }

    fun setOnClickItem(callback: (ModelIni) -> Unit) {
        this.onClickItem = callback
    }

    fun setOnClickDeleteItem(callback: (ModelIni) -> Unit) {
        this.onClickDeleteItem = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = IniViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.card_items_ini, parent, false)
    )

    override fun onBindViewHolder(holder: AdapterIni.IniViewHolder, position: Int) {
        val ini = iniList[position]
        holder.bindView(ini)
        holder.itemView.setOnClickListener{
            onClickItem?.invoke(ini)
        holder.hapusTombol.setOnClickListener{
            onClickDeleteItem?.invoke(ini)
        }
        }
    }

    override fun getItemCount(): Int {
        return iniList.size
    }

    class IniViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var id = view.findViewById<TextView>(R.id.tvId)
        private var nama = view.findViewById<TextView>(R.id.tvNama)
        private var email = view.findViewById<TextView>(R.id.tvEmail)
        var hapusTombol = view.findViewById<TextView>(R.id.hapusTombol)

        fun bindView(ini: ModelIni) {
            id.text = ini.id.toString()
            nama.text = ini.nama
            email.text = ini.email
        }
    }
}