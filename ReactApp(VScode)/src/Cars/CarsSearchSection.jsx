import React from 'react'; 

const SearchSection = ({ formData, prefList, branchList, makerList, typeList, handleChange, handleSearch, onAddClick }) => { 
    return ( 
        <div className="search-form-container"> 
            <div className="form-grid"> 
                <div className="form-group"> 
                    <label htmlFor="prefCode">都道府県</label> 
                    <select 
                        id="prefCode" 
                        name="prefCode" 
                        value={formData.prefCode} 
                        onChange={handleChange} 
                    > 
                        <option value="">選択してください</option> 
                        {prefList && prefList.map(item => ( 
                            <option key={item.prefCode} value={item.prefCode}> 
                                {item.prefName} 
                            </option> 
                        ))} 
                    </select> 
                </div> 

                <div className="form-group"> 
                    <label htmlFor="branchCode">支店</label> 
                    <select 
                        id="branchCode" 
                        name="branchCode" 
                        value={formData.branchCode} 
                        onChange={handleChange} 
                    > 
                        <option value="">選択してください</option> 
                        {branchList && branchList.map(item => ( 
                            <option key={item.branchCode} value={item.branchCode}> 
                                {item.branchName} 
                            </option> 
                        ))} 
                    </select> 
                </div> 

                <div className="form-group"> 
                    <label htmlFor="makerCode">メーカー</label> 
                    <select 
                        id="makerCode" 
                        name="makerCode" 
                        value={formData.makerCode} 
                        onChange={handleChange} 
                    > 
                        <option value="">選択してください</option> 
                        {makerList && makerList.map(item => ( 
                            <option key={item.makerCode} value={item.makerCode}> 
                                {item.makerName} 
                            </option> 
                        ))} 
                    </select> 
                </div> 

                <div className="form-group"> 
                    <label htmlFor="typeCode">タイプ</label> 
                    <select 
                        id="typeCode" 
                        name="typeCode" 
                        value={formData.typeCode} 
                        onChange={handleChange} 
                    > 
                        <option value="">選択してください</option> 
                        {typeList && typeList.map(item => ( 
                            <option key={item.typeCode} value={item.typeCode}> 
                                {item.typeName} 
                            </option> 
                        ))} 
                    </select> 
                </div> 

                <div className="form-group"> 
                    <label htmlFor="modelName">モデル名</label> 
                    <input 
                        type="text" 
                        id="modelName" 
                        name="modelName" 
                        value={formData.modelName} 
                        onChange={handleChange} 
                        placeholder="モデル名を入力" 
                    /> 
                </div> 
                
                <div className="form-group checkbox-group">
                    <input 
                        type="checkbox" 
                        id="ownerless" 
                        name="ownerless" 
                        checked={formData.ownerless} 
                        onChange={handleChange}
                    />
                    <label htmlFor="ownerless">元所有者なし</label>
                </div>
            </div> 

            <div className="search-button-group button-spacing">
                <button 
                    type="submit" 
                    className="btn btn-primary" 
                    onClick={handleSearch} 
                > 
                    検索 
                </button> 
                <button 
                    type="button" 
                    className="btn" 
                    onClick={onAddClick} 
                > 
                    新規追加 
                </button> 
            </div> 
        </div> 
    ); 
}; 

export default SearchSection;