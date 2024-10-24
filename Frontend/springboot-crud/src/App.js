import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './App.css';

const App = () => {
    const [parents, setParents] = useState([]);
    const [children, setChildren] = useState([]);
    const [newParent, setNewParent] = useState({ name: '', code: '' });
    const [newChild, setNewChild] = useState({ name: '', code: '', parentId: '' });
    const [editingParent, setEditingParent] = useState(null);
    const [editingChild, setEditingChild] = useState(null);

    // Fetch parents and children from the backend
    useEffect(() => {
        fetchParents();
        fetchChildren();
    }, []);

    const fetchParents = async () => {
        try {
            const response = await axios.get('http://localhost:8080/api/parents');
            setParents(response.data);
        } catch (error) {
            console.error('Error fetching parents:', error);
        }
    };

    const fetchChildren = async () => {
        try {
            const response = await axios.get('http://localhost:8080/api/children');
            setChildren(response.data);
        } catch (error) {
            console.error('Error fetching children:', error);
        }
    };

    // Handle parent creation or update
    const handleCreateOrUpdateParent = async () => {
        if (editingParent) {
            await axios.put(`http://localhost:8080/api/parents/${editingParent.id}`, newParent);
            setEditingParent(null);
        } else {
            await axios.post('http://localhost:8080/api/parents', newParent);
        }
        fetchParents(); // Refresh the list
        setNewParent({ name: '', code: '' });
    };

    // Handle child creation or update
    const handleCreateOrUpdateChild = async () => {
        const childData = {
            name: newChild.name,
            code: newChild.code,
            parent: { id: newChild.parentId }
        };

        if (editingChild) {
            await axios.put(`http://localhost:8080/api/children/${editingChild.id}`, childData);
            setEditingChild(null);
        } else {
            await axios.post('http://localhost:8080/api/children', childData);
        }

        fetchChildren(); // Refresh the list
        setNewChild({ name: '', code: '', parentId: '' });
    };

    // Handle parent edit
    const handleEditParent = (parent) => {
        setNewParent({ name: parent.name, code: parent.code });
        setEditingParent(parent);
    };

    // Handle child edit
    const handleEditChild = (child) => {
        setNewChild({ name: child.name, code: child.code, parentId: child.parent.id });
        setEditingChild(child);
    };

    // Handle parent deletion
    const handleDeleteParent = async (id) => {
        await axios.delete(`http://localhost:8080/api/parents/${id}`);
        fetchParents(); // Refresh the list
    };

    // Handle child deletion
    const handleDeleteChild = async (id) => {
        await axios.delete(`http://localhost:8080/api/children/${id}`);
        fetchChildren(); // Refresh the list
    };

    return (
        <div className="container">
            <h2 className="title">{editingParent ? "Edit Parent" : "Create Parent"}</h2>
            <div className="form-section">
                <input
                    type="text"
                    placeholder="Parent Name"
                    className="input-field"
                    value={newParent.name}
                    onChange={(e) => setNewParent({ ...newParent, name: e.target.value })}
                />
                <input
                    type="text"
                    placeholder="Parent Code"
                    className="input-field"
                    value={newParent.code}
                    onChange={(e) => setNewParent({ ...newParent, code: e.target.value })}
                />
                <button className="btn" onClick={handleCreateOrUpdateParent}>
                    {editingParent ? "Update Parent" : "Create Parent"}
                </button>
                {editingParent && (
                    <button className="btn cancel" onClick={() => { setEditingParent(null); setNewParent({ name: '', code: '' }); }}>
                        Cancel Edit
                    </button>
                )}
            </div>

            <h2 className="title">{editingChild ? "Edit Child" : "Create Child"}</h2>
            <div className="form-section">
                <input
                    type="text"
                    placeholder="Child Name"
                    className="input-field"
                    value={newChild.name}
                    onChange={(e) => setNewChild({ ...newChild, name: e.target.value })}
                />
                <input
                    type="text"
                    placeholder="Child Code"
                    className="input-field"
                    value={newChild.code}
                    onChange={(e) => setNewChild({ ...newChild, code: e.target.value })}
                />
                <select
                    className="input-field"
                    value={newChild.parentId}
                    onChange={(e) => setNewChild({ ...newChild, parentId: e.target.value })}
                >
                    <option value="">Select Parent</option>
                    {parents.map(parent => (
                        <option key={parent.id} value={parent.id}>
                            {parent.name}
                        </option>
                    ))}
                </select>
                <button className="btn" onClick={handleCreateOrUpdateChild}>
                    {editingChild ? "Update Child" : "Create Child"}
                </button>
                {editingChild && (
                    <button className="btn cancel" onClick={() => { setEditingChild(null); setNewChild({ name: '', code: '', parentId: '' }); }}>
                        Cancel Edit
                    </button>
                )}
            </div>

            <h2 className="title">Parents</h2>
            <ul className="list">
                {parents.map(parent => (
                    <li key={parent.id} className="list-item">
                        {parent.name} - {parent.code} 
                        <button className="btn edit" onClick={() => handleEditParent(parent)}>Edit</button>
                        <button className="btn delete" onClick={() => handleDeleteParent(parent.id)}>Delete</button>
                    </li>
                ))}
            </ul>

            <h2 className="title">Children</h2>
            <ul className="list">
                {children.map(child => (
                    <li key={child.id} className="list-item">
                        {child.name} - {child.code} (Parent: {child.parent.name})
                        <button className="btn edit" onClick={() => handleEditChild(child)}>Edit</button>
                        <button className="btn delete" onClick={() => handleDeleteChild(child.id)}>Delete</button>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default App;
