package com.estudos.animalsapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.estudos.animalsapp.databinding.FragmentListBinding
import com.estudos.animalsapp.model.Animal
import com.estudos.animalsapp.viewmodel.ListViewModel

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ListViewModel
    private val listAdapter = AnimalListAdapter(arrayListOf())

    private val animalListDataObserver = Observer<List<Animal>> { list ->
        list?.let {
            binding.animalList.visibility = View.VISIBLE
            listAdapter.updateAnimalList(it)
        }
    }
    private val loadingLiveDataObserver = Observer<Boolean> { isLoading ->
        binding.loadingView.visibility = if (isLoading) View.VISIBLE else View.GONE
        if (isLoading) {
            binding.listError.visibility = View.GONE
            binding.animalList.visibility = View.GONE
        }
    }
    private val errorLiveDataObserver = Observer<Boolean> { isError ->
        binding.listError.visibility = if (isError) View.VISIBLE else View.GONE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        watchObservers()
        setAdapter()
        setRefreshListener()
    }

    private fun setAdapter() {
        binding.animalList.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = listAdapter
        }
    }

    private fun setRefreshListener() {
        binding.refreshLayout.setOnRefreshListener {
            binding.animalList.visibility = View.GONE
            binding.listError.visibility = View.GONE
            binding.loadingView.visibility = View.VISIBLE
            viewModel.refresh()
            binding.refreshLayout.isRefreshing = false
        }
    }

    private fun watchObservers() {
        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.animals.observe(this, animalListDataObserver)
        viewModel.loading.observe(this, loadingLiveDataObserver)
        viewModel.loadError.observe(this, errorLiveDataObserver)
        viewModel.loadInfo()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}