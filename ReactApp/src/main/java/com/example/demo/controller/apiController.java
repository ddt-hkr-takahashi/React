package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.mapper.SalesInfoMapper;
import com.example.model.BranchesList;
import com.example.model.MakersList;
import com.example.model.OwnersList;
import com.example.model.PrefsList;
import com.example.model.StoresList;
import com.example.model.TypesList;

/**
 * APIエンドポイントを提供するRestControllerクラス。
 * 各種リスト情報を取得するためのAPIを提供。
 */
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

	/**
	 * 都道府県リストを取得する。
	 *
	 * @return 都道府県リスト
	 */
	@GetMapping("/prefs")
	public List<PrefsList> getPrefs() {
		return PrefsListMapper.select(c -> c);
	}

	/**
	 * 店舗リストを取得する。
	 *
	 * @return 店舗リスト
	 */
	@GetMapping("/stores")
	public List<StoresList> getStores() {
		return StoresListMapper.select(c -> c);
	}

	/**
	 * 支店リストを取得する。
	 *
	 * @param prefCode 都道府県コード（任意）
	 * @return 支店リスト
	 */
	@GetMapping("/branches")
	public List<BranchesList> getBranches(@RequestParam(required = false) String prefCode) {
		// prefCodeが存在する場合は、その都道府県に紐づく支店を検索
		if (prefCode != null && !prefCode.isEmpty()) {
			return SalesInfoMapper.findByPrefCode(prefCode);
		}
		// prefCodeが存在しない場合は、全ての支店を返す
		return BranchesListMapper.select(c -> c);
	}

	/**
	 * メーカーリストを取得する。
	 *
	 * @return メーカーリスト
	 */
	@GetMapping("/makers")
	public List<MakersList> getMakers() {
		return MakersListMapper.select(c -> c);
	}

	/**
	 * 種類リストを取得する。
	 *
	 * @return 種類リスト
	 */
	@GetMapping("/types")
	public List<TypesList> getTypes() {
		return TypesListMapper.select(c -> c);
	}

	/**
	 * モデルリストを取得する。
	 *
	 * @return モデルリスト
	 */
	@GetMapping("/models")
	public List<Map<String, Object>> getModels() {
		return SalesInfoMapper.selectModelsWithCombinedName();
	}

	/**
	 * 所有者リストを取得する。
	 *
	 * @return 所有者リスト
	 */
	@GetMapping("/owners")
	public List<OwnersList> getOwners() {
		return OwnersListMapper.select(c -> c);
	}

	/**
	 * 所有者リスト（名前）を取得する。
	 *
	 * @return 所有者リスト
	 */
	@GetMapping("/names")
	public List<OwnersList> getOwnersName() {
		return OwnersListMapper.select(c -> c);
	}

	/**
	 * 所有者リスト（性別）を取得する。
	 *
	 * @return 所有者リスト
	 */
	@GetMapping("/genders")
	public List<OwnersList> getSex() {
		return OwnersListMapper.select(c -> c);
	}

	/**
	 * 所有者リスト（年齢）を取得する。
	 *
	 * @return 所有者リスト
	 */
	@GetMapping("/age")
	public List<OwnersList> getAge() {
		return OwnersListMapper.select(c -> c);
	}

}