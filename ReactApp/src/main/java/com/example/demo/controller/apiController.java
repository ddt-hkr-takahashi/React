package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.SalesInfoDto;
import com.example.mapper.SalesInfoMapper;
import com.example.model.BranchesList;
import com.example.model.MakersList;
import com.example.model.OwnersList;
import com.example.model.PrefsList;
import com.example.model.StoresList;
import com.example.model.TypesList;

@RestController
@RequestMapping("/api")
public class apiController {

	@Autowired
	private com.example.mapper.OwnersListMapper OwnersListMapper;
	@Autowired
	private com.example.mapper.BranchesListMapper BranchesListMapper;
	@Autowired
	private com.example.mapper.StoresListMapper StoresListMapper;
	@Autowired
	private com.example.mapper.MakersListMapper MakersListMapper;
	@Autowired
	private com.example.mapper.PrefsListMapper PrefsListMapper;
	@Autowired
	private com.example.mapper.TypesListMapper TypesListMapper;
	@Autowired
	private SalesInfoMapper SalesInfoMapper;

	@GetMapping("/prefs")
	public List<PrefsList> getPrefs() {
		return PrefsListMapper.select(c -> c);
	}

	@GetMapping("/stores")
	public List<StoresList> getStores() {
		return StoresListMapper.select(c -> c);
	}

	@GetMapping("/branches")
	public List<BranchesList> getBranches(@RequestParam(required = false) String prefCode) {
		// prefCodeが存在する場合は、その都道府県に紐づく支店を検索
		if (prefCode != null && !prefCode.isEmpty()) {
			return SalesInfoMapper.findByPrefCode(prefCode);
		}
		// prefCodeが存在しない場合は、全ての支店を返す
		return BranchesListMapper.select(c -> c);
	}

	@GetMapping("/makers")
	public List<MakersList> getMakers() {
		return MakersListMapper.select(c -> c);
	}

	@GetMapping("/types")
	public List<TypesList> getTypes() {
		return TypesListMapper.select(c -> c);
	}

	@GetMapping("/models")
	public List<SalesInfoDto> getModels() {
		return SalesInfoMapper.selectModelsWithCombinedName();
	}

	@GetMapping("/owners")
	public List<OwnersList> getOwners() {
		return OwnersListMapper.select(c -> c);
	}

	@GetMapping("/names")
	public List<OwnersList> getOwnersName() {
		return OwnersListMapper.select(c -> c);
	}

	@GetMapping("/genders")
	public List<OwnersList> getSex() {
		return OwnersListMapper.select(c -> c);
	}

	@GetMapping("/age")
	public List<OwnersList> getAge() {
		return OwnersListMapper.select(c -> c);
	}

}
