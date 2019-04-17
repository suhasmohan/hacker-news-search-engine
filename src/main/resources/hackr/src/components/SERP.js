import React, {Component} from 'react';
import axios from 'axios';

import Story from "./Story";
import Comment from "./Comment";
import ReactPaginate from 'react-paginate';

class SERP extends Component {
    constructor(props) {
        super(props);
        this.state = {
            results: [],
            highlighting: {},
            numResults: 0,
            page: 0
        }
        this.search = {value: this.props.match.params.query};
        this.sort = {};
        this.defaultValue = this.props.match.params.query.split("+").join(" ");
    }

    fetchSearchResults(query) {
        axios.post('/api/search', {
            q: query,
            sort: this.sort.value,
            page: this.state.page
        })
            .then(response => {
                console.log(response);
                this.setState({
                    results: response.data.results,
                    highlighting: response.data.highlighting,
                    numResults: response.data.numResults
                });
            })
            .catch(function (error) {
                debugger;
                console.log(error);
            });
    }

    componentDidMount() {
        this.fetchSearchResults(this.props.match.params.query);
    }

    onSubmit(e) {
        e.preventDefault();
        let query = this.search.value.split(" ").join("+");
        this.props.history.push("/search/" + query);
        this.fetchSearchResults(this.search.value);

    }

    onChange(e) {
        e.preventDefault();
        this.fetchSearchResults(this.search.value);
    }

    renderResults() {
        return this.state.results.map((result, i) => {
            if (result.type == "story") {
                return (<Story data={result} key={i++} highlighting={this.state.highlighting[result.id]}/>);
            } else {
                return (<Comment data={result} key={i++} highlighting={this.state.highlighting[result.id]}/>);
            }

        });
    }

    handlePageClick = data => {
        debugger;
        this.setState({
            page: data.selected
        }, () => { debugger; this.fetchSearchResults(this.search.value);});

    };

    render() {
        return (
            <div className="container">
                <div className="row">
                    <div className="col-md-10 col-lg-8 col-xl-7">
                        <form onSubmit={this.onSubmit.bind(this)}>
                            <div className="form-row">
                                <div className="col-12 col-md-9 mb-2 mb-md-0">
                                    <input type="text" className="form-control form-control-lg"
                                           placeholder="Enter your query"
                                           ref={(input) => this.search = input}
                                           defaultValue={this.defaultValue}
                                    />
                                </div>
                                <div className="col-12 col-md-3">
                                    <button type="submit" className="btn btn-block btn-lg btn-primary">Search
                                    </button>
                                </div>
                            </div>
                            <div className="form-row">
                                <label htmlFor="sortBy" className="col-sm-2 col-form-label">Sort By</label>
                                <div className="col-sm-3">
                                    <select className="form-control" id="sortBy" onChange={this.onChange.bind(this)}
                                            ref={(sort) => this.sort = sort}>
                                        <option value="relevance">Relevance</option>
                                        <option value="time">Date</option>
                                        <option value="score">Score</option>
                                    </select>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
                <hr/>
                <span>Showing 1 - {Math.min(this.state.results.length, 10)} of {this.state.numResults.toLocaleString()} results</span>
                {this.renderResults()}
                <ReactPaginate
                    previousLabel={'«'}
                    nextLabel={'»'}
                    breakLabel={'...'}
                    breakClassName={'break-me'}
                    pageClassName={'page-item'}
                    previousClassName={'page-item'}
                    nextClassName={'page-item'}
                    pageLinkClassName={'page-link'}
                    previousLinkClassName={'page-link'}
                    nextLinkClassName={'page-link'}
                    pageCount={this.state.numResults/10}
                    marginPagesDisplayed={2}
                    pageRangeDisplayed={3}
                    onPageChange={this.handlePageClick}
                    containerClassName={'pagination'}
                    subContainerClassName={'pages pagination'}
                    activeClassName={'active'}
                />
            </div>
        );
    }
}

export default SERP;