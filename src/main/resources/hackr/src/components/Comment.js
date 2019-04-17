import React, {Component} from 'react';
import axios from "axios";
import TimeAgo from 'react-timeago';


class Comment extends Component {
    constructor(props) {
        super(props);
        this.state = {
            story: {}
        }
    }

    getHighlightingHTML() {
        return {__html: this.props.highlighting[Object.keys(this.props.highlighting)[0]][0]};

    }

    getURL(id) {
        if (this.state.story.url) {
            return this.state.story.url;
        } else {
            return this.getHNStoryURL(id);
        }

    }

    getHNStoryURL(id) {
        return "https://news.ycombinator.com/item?id=" + id;
    }

    getHNUserURL(id) {
        return "https://news.ycombinator.com/user?id=" + id;
    }

    getStoryById() {
        axios.get('/api/getStoryDetails?id=' + this.props.data.parent)
            .then(response => {
                console.log(response);
                this.setState({
                    story: response.data,
                });
            })
            .catch(function (error) {
                debugger;
                console.log(error);
            });
    }

    componentDidMount() {
        this.getStoryById();
    }

    getStoryTitle() {
        return {__html: this.stringTruncate((this.state.story.title ? this.state.story.title : this.state.story.text), 200)};
    }

    stringTruncate(str, length){
        var dots = str.length > length ? '...' : '';
        return str.substring(0, length)+dots;
    }


    render() {
        return (
            <div className="card">
                {this.state.story.id ?
                    (<div className="card-body">
                    <div>
                        <a href={this.getURL(this.state.story.id)} target="_blank">
                            {"title" in this.props.highlighting ?
                                <h4 dangerouslySetInnerHTML={this.getHighlightingHTML()}/> :
                                <h4 dangerouslySetInnerHTML={this.getStoryTitle()}/>}
                        </a>
                    </div>
                    <div>
                        <a href={this.getURL(this.state.story.id)} target="_blank">{this.getURL(this.state.story.id)}</a>
                    </div>

                    <div>
                        <a href={this.getHNUserURL(this.state.story.by)} target="_blank">{this.state.story.by}</a> | <a
                        href={this.getHNStoryURL(this.state.story.id)} target="_blank">{this.state.story.score} points</a> | <a
                        href={this.getHNStoryURL(this.state.story.id)} target="_blank" title={this.props.data.time} ><TimeAgo date={this.props.data.time}/></a>
                    </div>

                    <div className="comment-margin">
                        {"text" in this.props.highlighting ?
                            <span dangerouslySetInnerHTML={this.getHighlightingHTML()}/> :
                            this.props.data.text ? <span>{this.props.data.text}</span> : <span/>}
                        <div>
                            <a href={this.getHNUserURL(this.props.data.by)} target="_blank">{this.props.data.by}</a> | <a
                            href={this.getHNStoryURL(this.props.data.id)} target="_blank">{this.props.data.score} points</a> | <a
                            href={this.getHNStoryURL(this.props.data.id)} target="_blank"><TimeAgo date={this.props.data.time}/></a>
                        </div>
                    </div>
                </div>) :
                    (<div></div>)}
            </div>
        );
    }
}

export default Comment;